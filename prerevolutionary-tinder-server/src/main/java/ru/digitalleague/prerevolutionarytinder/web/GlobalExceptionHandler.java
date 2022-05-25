package ru.digitalleague.prerevolutionarytinder.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.digitalleague.prerevolutionarytinder.exception.BusinessLogicException;
import ru.digitalleague.prerevolutionarytinder.service.MessageService;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends HttpExceptionResolver{

    @Autowired
    public GlobalExceptionHandler(MessageService messageService) {
        super(messageService);
    }

    @ExceptionHandler(BusinessLogicException.class)
    @Override
    protected ResponseEntity<ErrorResponse> resolveBusinessLogicException(HttpServletRequest request,
                                                                          BusinessLogicException exception) {
        return super.resolveBusinessLogicException(request, exception);
    }
}
