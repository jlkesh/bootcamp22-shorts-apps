package dev.jlkeesh.shorts.utils;

import com.google.common.hash.Hashing;
import dev.jlkeesh.shorts.config.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BaseUtils {
    private final SessionUser sessionUser;

    public String hashUrl(@NonNull String url) {
        return Hashing
                .murmur3_32_fixed()
                .hashString((url + sessionUser.id() + System.currentTimeMillis()), StandardCharsets.UTF_8)
                .toString();
    }

    public String activationCode(@NonNull Long id) {
        return Base64.getUrlEncoder().encodeToString(
                (UUID.randomUUID().toString() + System.currentTimeMillis() + id)
                        .getBytes());
    }
}
