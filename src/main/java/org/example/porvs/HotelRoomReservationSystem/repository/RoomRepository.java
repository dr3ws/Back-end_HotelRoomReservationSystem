package org.example.porvs.HotelRoomReservationSystem.repository;

import org.example.porvs.HotelRoomReservationSystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
    Optional<Room> findFirstById(UUID id);

    @Query(value = "select id from Room where id not in :ids")
    List<UUID> findAllNotReservedRoomIds(List<UUID> ids);

    @Query(value = "select id from Room")
    List<UUID> findAllIds();
}
