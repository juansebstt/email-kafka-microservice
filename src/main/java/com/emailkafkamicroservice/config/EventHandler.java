package com.emailkafkamicroservice.config;

import com.emailkafkamicroservice.common.event.LetterEvent;
import com.emailkafkamicroservice.common.event.NotificationEvent;
import com.emailkafkamicroservice.common.event.PackageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class EventHandler {

    @Bean
    public Function<Flux<LetterEvent>, Flux<NotificationEvent>> letterBinding(final LetterEligibleProcessor processor) {
        return processor;
    }

    @Bean
    public Function<Flux<PackageEvent>, Flux<PackageEvent>> packageBinding(final PackageEligibleProcessor processor) {
        return processor::process;
    }

}
