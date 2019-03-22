package org.kimbs.jpademo.logging;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ControllerLog implements Serializable {

    private static final long serialVersionUID = 4732607515344279416L;
    
    private String httpMethod;
    private String urlPattern;
    private ZonedDateTime requestedAt;

    public ControllerLog(String method, String urlPattern, ZonedDateTime requestedAt) {
        this.httpMethod = method;
        this.urlPattern = urlPattern;
        this.requestedAt = requestedAt;
    }
}