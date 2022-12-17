package org.example.porvs.HotelRoomReservationSystem.service;

import org.example.porvs.HotelRoomReservationSystem.models.ReservationRoom;
import org.example.porvs.HotelRoomReservationSystem.models.Room;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;

import java.math.BigDecimal;
import java.util.UUID;

public interface ReservationRoomService {
    ReservationRoom reservation(Room room, ReservationRequest reservationRequest);

    void pay(UUID reservationId, BigDecimal price);
}
