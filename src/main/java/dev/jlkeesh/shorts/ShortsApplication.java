package dev.jlkeesh.shorts;

import dev.jlkeesh.shorts.config.security.SessionUser;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class ShortsApplication {
    private final Environment env;

    public ShortsApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortsApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorAware(SessionUser sessionUser) {
        return () -> Optional.of(sessionUser.getId());
    }


    @PostConstruct
    public void postConstruct() throws IOException {
        Path path = Path.of(env.getRequiredProperty("application.log.path"));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
