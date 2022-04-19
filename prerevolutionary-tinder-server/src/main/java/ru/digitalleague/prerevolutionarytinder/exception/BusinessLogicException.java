package ru.digitalleague.prerevolutionarytinder.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException{

    private String userMessage;
    private boolean needLogStackTrace = true;

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public BusinessLogicException(String message, String userMessage, boolean needLogStackTrace) {
        super(message);
        this.userMessage = userMessage;
        this.needLogStackTrace = needLogStackTrace;
    }
}
