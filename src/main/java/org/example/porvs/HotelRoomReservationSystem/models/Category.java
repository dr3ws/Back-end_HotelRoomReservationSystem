package org.example.porvs.HotelRoomReservationSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal price;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Schedule> schedule;
}
