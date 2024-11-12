package com.emailkafkamicroservice.service.impl;

import com.emailkafkamicroservice.common.event.LetterEvent;
import com.emailkafkamicroservice.common.event.NotificationEvent;
import com.emailkafkamicroservice.service.LetterEligibilityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class LetterEligibilityServiceImpl implements LetterEligibilityService {

    @Override
    public Mono<NotificationEvent> eligibilityLetter(LetterEvent letterEvent) {
        return Mono.just(letterEvent)
                .flatMap(this::checkIsEligible)
                .map(given -> NotificationEvent.builder()
                        .receiverEmail(letterEvent.getReceiverEmail())
                        .build()
                );
    }

    private Mono<LetterEvent> checkIsEligible(LetterEvent letterEvent) {
        return Mono.just(letterEvent)
                .filter(Objects::nonNull)
                .map(given -> letterEvent);
    }
}
