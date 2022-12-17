package org.example.porvs.HotelRoomReservationSystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HotelRoomException extends RuntimeException{
    private final HttpStatus status;

    public HotelRoomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static HotelRoomException internal(String message) {
        return new HotelRoomException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static HotelRoomException unauthorized(String message) {
        return new HotelRoomException(message, HttpStatus.UNAUTHORIZED);
    }

    public static HotelRoomException notFound(String message) {
        return new HotelRoomException(message, HttpStatus.NOT_FOUND);
    }

    public static HotelRoomException forbidden(String message) {
        return new HotelRoomException(message, HttpStatus.FORBIDDEN);
    }
}
