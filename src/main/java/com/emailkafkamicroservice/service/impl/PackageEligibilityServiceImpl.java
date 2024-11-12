package com.emailkafkamicroservice.service.impl;

import com.emailkafkamicroservice.common.event.PackageEvent;
import com.emailkafkamicroservice.service.PackageEligibilityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class PackageEligibilityServiceImpl implements PackageEligibilityService {

    @Override
    public Mono<PackageEvent> eligibilityPackage(PackageEvent packageEvent) {
        return Mono.just(packageEvent)
                .flatMap(this::checkIsEligible)
                .map(given -> packageEvent);
    }

    private Mono<PackageEvent> checkIsEligible(PackageEvent packageEvent) {
        return Mono.just(packageEvent)
                .filter(Objects::nonNull)
                .map(given -> packageEvent);
    }
}
