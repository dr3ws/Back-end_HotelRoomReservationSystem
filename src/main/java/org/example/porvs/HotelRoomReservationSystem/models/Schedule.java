package org.example.porvs.HotelRoomReservationSystem.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate date;

    private BigDecimal coef = BigDecimal.ONE;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
