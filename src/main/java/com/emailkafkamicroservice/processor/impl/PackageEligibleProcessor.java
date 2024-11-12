package com.emailkafkamicroservice.processor.impl;

import com.emailkafkamicroservice.common.event.PackageEvent;
import com.emailkafkamicroservice.processor.ProcessorSpec;
import com.emailkafkamicroservice.service.PackageEligibilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class PackageEligibleProcessor implements ProcessorSpec<PackageEvent> {

    private final PackageEligibilityService packageEligibilityService;

    @Autowired
    public PackageEligibleProcessor(PackageEligibilityService packageEligibilityService) {
        this.packageEligibilityService = packageEligibilityService;
    }

    @Override
    public Flux<PackageEvent> process(Flux<PackageEvent> inbound) {
        return inbound.doOnNext(entryEvent -> log.info("Received event"))
                .map(this.packageEligibilityService::eligibilityPackage)
                .map(given -> inbound.blockFirst())
                .subscribeOn(Schedulers.parallel());
    }

}
