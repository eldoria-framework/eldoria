package dev.eldoria.amulet.configuration.usecase;


import dev.eldoria.amulet.core.usecase.enrich.Amulet;
import dev.eldoria.amulet.core.usecase.enrich.EnrichCodex;
import dev.eldoria.amulet.core.usecase.process.amulet.ProcessUpdatePresenceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class UseCasesConfiguration {

    @Scope(SCOPE_SINGLETON)
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public EnrichCodex enrichContext() {
        return new EnrichCodex();
    }

    @Bean
    public ProcessUpdatePresenceUseCase processUpdatePresenceUseCase(Amulet amulet) {
        return new ProcessUpdatePresenceUseCase(amulet);
    }

}