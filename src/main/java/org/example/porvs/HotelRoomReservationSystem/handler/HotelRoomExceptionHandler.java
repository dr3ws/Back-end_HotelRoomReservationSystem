package org.example.porvs.HotelRoomReservationSystem.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.porvs.HotelRoomReservationSystem.exception.HotelRoomException;
import org.example.porvs.hotelRoomReservationSystem.dto.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class HotelRoomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HotelRoomException.class)
    public ResponseEntity<Object> exceptionHandler(HotelRoomException exception) {
        log.error("Error occurred: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ErrorMessage()
                        .code(exception.getStatus().value())
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .error(exception.getStatus().getReasonPhrase()));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        return super.handleBindException(ex, headers, status, request);
    }

}
