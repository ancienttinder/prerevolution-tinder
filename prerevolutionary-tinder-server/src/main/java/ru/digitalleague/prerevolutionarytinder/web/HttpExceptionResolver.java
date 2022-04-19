package ru.digitalleague.prerevolutionarytinder.web;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.digitalleague.prerevolutionarytinder.exception.BusinessLogicException;
import ru.digitalleague.prerevolutionarytinder.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Slf4j
public abstract class HttpExceptionResolver {

    private MessageService messageService;

    public HttpExceptionResolver(@NonNull MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseBody
    protected ResponseEntity<ErrorResponse> resolveBusinessLogicException(HttpServletRequest request, BusinessLogicException exception) {
        logRequestException(request, exception);

        String message;
        if (StringUtils.isEmpty(exception.getUserMessage())) {
            message = messageService.getMessage("common.exception", exception.getMessage());
        } else {
            message = exception.getUserMessage();
        }
        return new ResponseEntity<>(ErrorResponse.build(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logRequestException(HttpServletRequest request, BusinessLogicException exception) {
        log.debug("Unexpected exception processing request: " + request.getRequestURI());
        if (exception.isNeedLogStackTrace()) {
            log.error("Exception: ", exception);
        } else {
            log.error(MessageFormat.format("Exception message: {0}", exception.getMessage()));
        }
    }
}
