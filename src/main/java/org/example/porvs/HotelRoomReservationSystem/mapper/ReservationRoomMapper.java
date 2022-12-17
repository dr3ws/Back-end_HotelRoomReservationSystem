package org.example.porvs.HotelRoomReservationSystem.mapper;

import org.example.porvs.HotelRoomReservationSystem.models.ReservationRoom;
import org.example.porvs.HotelRoomReservationSystem.models.Room;
import org.example.porvs.HotelRoomReservationSystem.models.Schedule;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(imports = LocalDate.class)
public interface ReservationRoomMapper {
    @Mapping(target = "reservationDate", expression = "java(LocalDate.now())")
    @Mapping(target = "checkInDate", source = "reservationRequest.checkInDate")
    @Mapping(target = "checkOutDate", source = "reservationRequest.checkOutDate")
    @Mapping(target = "firstName", source = "reservationRequest.firstName")
    @Mapping(target = "lastName", source = "reservationRequest.lastName")
    @Mapping(target = "email", source = "reservationRequest.email")
    @Mapping(target = "phone", source = "reservationRequest.phone")
    @Mapping(target = "price", expression = "java(mapPrice(room, reservationRequest))")
    @Mapping(target = "room", source = "room")
    ReservationRoom map(Room room, ReservationRequest reservationRequest);

    ReservationResponse map(ReservationRoom room);

    default BigDecimal mapPrice(Room room, ReservationRequest reservationRequest) {
        return reservationRequest.getCheckInDate().datesUntil(reservationRequest.getCheckOutDate())
                .map(it ->
                        room.getCategory().getPrice().multiply(room.getCategory().getSchedule().stream().filter(s ->
                                s.getDate().equals(it)).findFirst().map(Schedule::getCoef).orElseGet(() ->
                                BigDecimal.ONE)
                        )
                ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
