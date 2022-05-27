package ru.digitalleague.prerevolutionarytinder.web;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
public class ErrorResponse {
    private String level = "ERROR";//todo константа
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
    //todo метод не используется
    public static ErrorResponse build(String message, Exception exception) {
        return build(message, exception != null ? exception.getStackTrace() : null, "ERROR");//todo юзать константу
    }

    public static ErrorResponse build(String message) {
        return build(message, null, "ERROR");
    }//todo юзать константу
}
