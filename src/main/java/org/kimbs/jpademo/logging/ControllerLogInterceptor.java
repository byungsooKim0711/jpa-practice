package org.kimbs.jpademo.logging;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import reactor.core.publisher.FluxSink;

@Component
public class ControllerLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private FluxSink<ControllerLog> controllerLogSink;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ControllerLog controllerLog = new ControllerLog(
            request.getMethod(),
            (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE),
            ZonedDateTime.now()
        );
        controllerLogSink.next(controllerLog);

        return true;
    }
}