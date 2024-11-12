package com.emailkafkamicroservice.service;

import com.emailkafkamicroservice.common.event.PackageEvent;
import reactor.core.publisher.Mono;

public interface PackageEligibilityService {
    Mono<PackageEvent> eligibilityPackage(PackageEvent packageEvent);
}
