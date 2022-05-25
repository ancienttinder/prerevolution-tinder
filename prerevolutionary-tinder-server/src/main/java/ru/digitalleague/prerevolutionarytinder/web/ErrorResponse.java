package ru.digitalleague.prerevolutionarytinder.web;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
public class ErrorResponse {
    private String level = "ERROR";
    private String message;
    private String[] stacktrace;

    public static ErrorResponse build(String message, StackTraceElement[] stacktrace, String level) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setLevel(level);
        errorResponse.setMessage(message);
        if (stacktrace != null) {
            errorResponse.setStacktrace(Arrays.stream(stacktrace).map(StackTraceElement::toString).toArray(String[]::new));
        }
        return errorResponse;
    }

    public static ErrorResponse build(String message, Exception exception) {
        return build(message, exception != null ? exception.getStackTrace() : null, "ERROR");
    }

    public static ErrorResponse build(String message) {
        return build(message, null, "ERROR");
    }
}
