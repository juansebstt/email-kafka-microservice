package com.emailkafkamicroservice.service;

import com.emailkafkamicroservice.common.event.LetterEvent;
import com.emailkafkamicroservice.common.event.NotificationEvent;
import reactor.core.publisher.Mono;

public interface LetterEligibilityService {
    Mono<NotificationEvent> eligibilityLetter(LetterEvent letterEvent);
}
