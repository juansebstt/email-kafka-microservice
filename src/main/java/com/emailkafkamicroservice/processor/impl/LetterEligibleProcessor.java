package com.emailkafkamicroservice.processor.impl;

import com.emailkafkamicroservice.common.event.LetterEvent;
import com.emailkafkamicroservice.common.event.NotificationEvent;
import com.emailkafkamicroservice.service.LetterEligibilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class LetterEligibleProcessor implements Function<Flux<LetterEvent>, Flux<NotificationEvent>> {

    private final LetterEligibilityService letterEligibilityService;

    @Override
    public Flux<NotificationEvent> apply(Flux<LetterEvent> inbound) {
        return inbound.doOnNext(entryEvent -> log.info("Received event"))
                .flatMap(letterEligibilityService::eligibilityLetter)
                .doOnNext(notificationEvent -> log.info("Notification Event: {}", notificationEvent))
                .subscribeOn(Schedulers.parallel());
    }
    
}
