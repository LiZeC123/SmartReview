package top.lizec.smartreview.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import top.lizec.smartreview.exception.NoPermissionException;
import top.lizec.smartreview.response.Result;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NoPermissionException.class)
    public Result<?> handleNoPermissionException(HttpServletRequest request, NoPermissionException exception) {
        logger.error("Request: " + request.getRequestURI() + ": Throw Exception -> " + exception);
        return Result.failure(exception.getMessage());
    }

}
