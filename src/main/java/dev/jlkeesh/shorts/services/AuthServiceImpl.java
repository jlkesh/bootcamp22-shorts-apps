package dev.jlkeesh.shorts.services;

import dev.jlkeesh.shorts.config.security.JwtTokenUtil;
import dev.jlkeesh.shorts.config.security.SessionUser;
import dev.jlkeesh.shorts.dto.auth.AuthUserCreateDTO;
import dev.jlkeesh.shorts.dto.auth.TokenRequest;
import dev.jlkeesh.shorts.entities.AuthUser;
import dev.jlkeesh.shorts.entities.AuthUserOtp;
import dev.jlkeesh.shorts.mappers.AuthUserMapper;
import dev.jlkeesh.shorts.repositories.AuthUserOtpRepository;
import dev.jlkeesh.shorts.repositories.AuthUserRepository;
import dev.jlkeesh.shorts.utils.BaseUtils;
import dev.jlkeesh.shorts.utils.MailSenderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUserMapper authUserMapper;
    private final AuthUserRepository authUserRepository;
    private final AuthUserOtpRepository authUserOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final BaseUtils utils;
    private final MailSenderService mailService;
    private final SessionUser sessionUser;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            AuthUserMapper authUserMapper,
            AuthUserRepository authUserRepository,
            AuthUserOtpRepository authUserOtpRepository,
            PasswordEncoder passwordEncoder,
            BaseUtils utils,
            MailSenderService mailService,
            SessionUser sessionUser,
            JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.authUserMapper = authUserMapper;
        this.authUserRepository = authUserRepository;
        this.authUserOtpRepository = authUserOtpRepository;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
        this.mailService = mailService;
        this.sessionUser = sessionUser;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.username();
        String password = tokenRequest.password();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtTokenUtil.generateToken(username);
    }

    @Override
    public AuthUser create(@NotNull AuthUserCreateDTO dto) {
        AuthUser authUser = authUserMapper.toEntity(dto);
        authUser.setPassword(passwordEncoder.encode(dto.password()));
        authUser.setRole("USER");
        AuthUserOtp authUserOtp = AuthUserOtp.childBuilder()
                .createdBy(authUser.getId())
                .code(utils.activationCode(authUser.getId()))
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        String url = "http://localhost:8080/api/auth/activate/" + authUserOtp.getCode();

        Map<String, String> model = Map.of(
                "username", authUser.getUsername(),
                "to", authUser.getEmail(),
                "url", url);
        authUserOtpRepository.save(authUserOtp);
        mailService.sendActivationMail(model);
        return authUserRepository.save(authUser);
    }

    @Override
    /*@Transactional (noRollbackFor = RuntimeException.class)*/
    public boolean activateUserByOTP(@NonNull String code) {
        AuthUserOtp authUserOtp = authUserOtpRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("OTP code is invalid"));

        if (authUserOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            authUserOtp.setDeleted(true);
            authUserOtpRepository.save(authUserOtp);
            throw new RuntimeException("OTP code is invalid");
        }
        authUserRepository.activeUser(authUserOtp.getCreatedBy());
        return true;
    }

}
