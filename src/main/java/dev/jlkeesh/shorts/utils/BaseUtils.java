package dev.jlkeesh.shorts.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import dev.jlkeesh.shorts.config.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class BaseUtils {
    private final SessionUser sessionUser;

    public String hashUrl(@NonNull String url) {
        return Hashing
                .murmur3_32_fixed()
                .hashString((url + sessionUser.getId() + System.currentTimeMillis()), StandardCharsets.UTF_8)
                .toString();
    }
}
