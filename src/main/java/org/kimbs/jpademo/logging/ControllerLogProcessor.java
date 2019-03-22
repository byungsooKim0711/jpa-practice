package org.kimbs.jpademo.logging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class ControllerLogProcessor {

    private final Flux<ControllerLog> controllerLogFlux;

    private Disposable disposable;

    public ControllerLogProcessor(Flux<ControllerLog> controllerLogFlux) {
        this.controllerLogFlux = controllerLogFlux;
    }

    @PostConstruct
    public void init() {
        disposable = controllerLogFlux.subscribe(it -> log.info(it.toString()));
    }

    @PreDestroy
    public void destroy() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}