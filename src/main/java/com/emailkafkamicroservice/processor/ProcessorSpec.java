package com.emailkafkamicroservice.processor;

import reactor.core.publisher.Flux;

public interface ProcessorSpec<T> {
    Flux<T> process(Flux<T> inbound);
}
