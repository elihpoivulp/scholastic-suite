package org.npg.scholastic_suite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@JsonPropertyOrder({"code", "type", "message", "path", "details"})
public class AppApiErrorResponse {
    private int code;
    private HttpStatus type;
    private String message;
    private String path;
    private Set<ErrorDetails> details;

    public AppApiErrorResponse(HttpStatus code, String message, String path, Set<String> details) {
        this.code = code.value();
        this.type = code;
        this.message = message;
        this.path = path;
        this.details = details == null ? null : details.stream().map(this::toErrorDetails).collect(Collectors.toSet());
    }

    public static AppApiErrorResponse build(HttpStatus code, String message, String path, Set<String> details) {
        return new AppApiErrorResponse(code, message, path, details);
    }

    private ErrorDetails toErrorDetails(String message) {
        String[] messages = message.split(":");
        return new ErrorDetails(messages[0], messages[1]);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErrorDetails {
        private String property;
        private String violation;
    }
}
