package kea.exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(),getURIPath(request));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiException apiException = new ApiException(ex.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now(),getURIPath(request));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    private String getURIPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
