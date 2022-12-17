package org.example.porvs.HotelRoomReservationSystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.porvs.HotelRoomReservationSystem.exception.HotelRoomException;
import org.example.porvs.HotelRoomReservationSystem.mapper.ReservationRoomMapper;
import org.example.porvs.HotelRoomReservationSystem.models.ReservationRoom;
import org.example.porvs.HotelRoomReservationSystem.models.Room;
import org.example.porvs.HotelRoomReservationSystem.repository.ReservationRoomRepository;
import org.example.porvs.HotelRoomReservationSystem.service.ReservationRoomService;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationRoomService {
    private final ReservationRoomRepository repository;
    private final ReservationRoomMapper mapper;

    @Override
    public ReservationRoom reservation(Room room, ReservationRequest reservationRequest) {
        try {
            var reservation = mapper.map(room, reservationRequest);
            if (checkReservation(reservation)) {
                return repository.save(reservation);
            } else
                throw HotelRoomException.internal("Данная комната уже имеет бронь на текущую дату!");
        } catch (Exception e) {
            throw HotelRoomException.internal("Резервирование комнаты " + room.getNumber() + " не удалось\n" + e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pay(UUID reservationId, BigDecimal price) {
        var reservation = repository.getReferenceById(reservationId);

        if (reservation.getPrice().compareTo(price) > 0)
            throw HotelRoomException.internal("Недостаточный перевод денежной суммы");

        reservation.setPaid(true);
        repository.save(reservation);
    }

    private boolean checkReservation(ReservationRoom reservation) {
        for (int i = 0; i < repository.findAll().size(); i++) {
            if (repository.findAll().get(i).getRoom().getId().equals(reservation.getRoom().getId())) {
                if ((reservation.getCheckInDate().isBefore(repository.findAll().get(i).getCheckInDate()) && reservation.getCheckOutDate().isBefore(repository.findAll().get(i).getCheckInDate())) ||
                        (reservation.getCheckInDate().isAfter(repository.findAll().get(i).getCheckOutDate()) && reservation.getCheckOutDate().isAfter(repository.findAll().get(i).getCheckOutDate()))) {
                    System.out.println(repository.findAll().get(i).getCheckInDate());
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
