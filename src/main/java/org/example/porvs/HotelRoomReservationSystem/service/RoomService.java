package org.example.porvs.HotelRoomReservationSystem.service;

import org.example.porvs.HotelRoomReservationSystem.models.ReservationRoom;
import org.example.porvs.HotelRoomReservationSystem.models.Room;
import org.example.porvs.hotelRoomReservationSystem.dto.*;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    Room get(UUID roomId);

    List<Room> findAllRooms();

    List<Room> findAllRoomsByFilter(Filter filter, Page page, List<OrderItem> orderItem);

    ReservationRoom reservation(UUID roomId, ReservationRequest reservationRequest);
}
