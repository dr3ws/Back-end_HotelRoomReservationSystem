package org.example.porvs.HotelRoomReservationSystem.service;

import org.example.porvs.HotelRoomReservationSystem.models.ReservationRoom;
import org.example.porvs.hotelRoomReservationSystem.dto.Filter;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationResponse;
import org.example.porvs.hotelRoomReservationSystem.dto.Room;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientService {
    Room getRoom(UUID id);

    List<Room> getRooms(Filter filter);

    ReservationResponse reservationRoom(UUID roomId, ReservationRequest reservationRequest);

    void pay(UUID reservationId, BigDecimal price);
}
