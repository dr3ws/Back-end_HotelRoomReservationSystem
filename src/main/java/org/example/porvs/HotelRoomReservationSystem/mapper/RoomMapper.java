package org.example.porvs.HotelRoomReservationSystem.mapper;

import org.example.porvs.hotelRoomReservationSystem.dto.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Mapping(target = "price", source = "category.price")
    Room map(org.example.porvs.HotelRoomReservationSystem.models.Room room);

    List<Room> map(List<org.example.porvs.HotelRoomReservationSystem.models.Room> room);
}
