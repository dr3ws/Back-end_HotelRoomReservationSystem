package org.example.porvs.HotelRoomReservationSystem.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.example.porvs.HotelRoomReservationSystem.service.ClientService;
import org.example.porvs.hotelRoomReservationSystem.api.ClientApi;
import org.example.porvs.hotelRoomReservationSystem.dto.Filter;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationRequest;
import org.example.porvs.hotelRoomReservationSystem.dto.ReservationResponse;
import org.example.porvs.hotelRoomReservationSystem.dto.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
// | |
// v v ТАК ДЕЛАТЬ НЕЛЬЗЯ
@CrossOrigin
@Tag(name = "Client", description = "API, доступное неавторизованным клиентам")
public class ClientController implements ClientApi {
    private final ClientService clientService;

    @Override
    public ResponseEntity<Room> getRoom(UUID roomId) {
        return ResponseEntity.ok(clientService.getRoom(roomId));
    }

    @Override
    public ResponseEntity<List<Room>> getRooms(@Valid Filter filter) {
        return ResponseEntity.ok(clientService.getRooms(filter));
    }

    @Override
    public ResponseEntity<ReservationResponse> reservationRoom(UUID roomId, @Valid ReservationRequest reservationRequest) {
        return ResponseEntity.ok(clientService.reservationRoom(roomId, reservationRequest));
    }

    @Override
    public ResponseEntity<Void> payReservation(UUID id, @Valid BigDecimal body) {
        clientService.pay(id, body);
        return ResponseEntity.ok().build();
    }
}