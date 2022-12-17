package org.example.porvs.HotelRoomReservationSystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.porvs.HotelRoomReservationSystem.mapper.ReservationRoomMapper;
import org.example.porvs.HotelRoomReservationSystem.mapper.RoomMapper;
import org.example.porvs.HotelRoomReservationSystem.service.ClientService;
import org.example.porvs.HotelRoomReservationSystem.service.ReservationRoomService;
import org.example.porvs.HotelRoomReservationSystem.service.RoomService;
import org.example.porvs.hotelRoomReservationSystem.dto.Filter;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationResponse;
import org.example.porvs.hotelRoomReservationSystem.dto.Room;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final ReservationRoomService reservationRoomService;
    private final ReservationRoomMapper reservationRoomMapper;

    @Override
    public ReservationResponse reservationRoom(UUID roomId, ReservationRequest reservationRequest) {
        log.info("reservation room {}", roomId);
        return reservationRoomMapper.map(roomService.reservation(roomId, reservationRequest));
    }

    @Override
    public List<Room> getRooms(Filter filter) {
        log.info("get rooms by filter {}", filter);
        return roomMapper.map(roomService.findAllRoomsByFilter(filter, filter.getPage(), filter.getOrder()));
    }

    @Override
    public Room getRoom(UUID id) {
        log.info("get room {}", id);
        return roomMapper.map(roomService.get(id));
    }

    @Override
    public void pay(UUID reservationId, BigDecimal price) {
        reservationRoomService.pay(reservationId, price);
    }
}
