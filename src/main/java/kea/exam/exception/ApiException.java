package kea.exam.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ApiException {
    private final String message;
    private final String error;
    private final int statusCode;
    private final ZonedDateTime timeStamp;
    private final String path;

    public ApiException(String message, HttpStatus error, ZonedDateTime timeStamp,String path) {
        this.message = message;
        this.statusCode = error.value();
        this.error = error.getReasonPhrase();
        this.timeStamp = timeStamp;
        this.path = path;
    }
}
