package com.epam.recreation_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    AuditorAware<Long> getAuditAware() {
        return new AuditorAwareImpl();
    }
}
