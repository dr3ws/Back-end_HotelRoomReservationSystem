package org.example.porvs.HotelRoomReservationSystem.repository;

import org.example.porvs.HotelRoomReservationSystem.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

}
