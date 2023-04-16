package dev.jlkeesh.shorts;

import dev.jlkeesh.shorts.config.security.SessionUser;
import dev.jlkeesh.shorts.utils.Report;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class ShortsApplication {
    private final Environment env;
    private final Report report;

    public ShortsApplication(Environment env, Report report) {
        this.env = env;
        this.report = report;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortsApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorAware(SessionUser sessionUser) {
        return () -> Optional.of(sessionUser.id());
    }

    @Scheduled(cron = "0 0 0 * * mon")
    public void reporting(){
        report.report();
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        Path path = Path.of(env.getRequiredProperty("application.log.path"));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
