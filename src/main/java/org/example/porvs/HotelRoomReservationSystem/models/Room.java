package org.example.porvs.HotelRoomReservationSystem.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Room {
    @Id
    @GeneratedValue
    private UUID id;
    private Integer number;
    private Integer places;
    private String image;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "room")
    private List<ReservationRoom> reservation;
}
