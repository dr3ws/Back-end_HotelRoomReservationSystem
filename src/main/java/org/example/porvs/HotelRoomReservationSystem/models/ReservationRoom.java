package org.example.porvs.HotelRoomReservationSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRoom {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate reservationDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private BigDecimal price;
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

}
