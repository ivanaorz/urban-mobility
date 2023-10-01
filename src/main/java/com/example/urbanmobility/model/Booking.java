package com.example.urbanmobility.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //enables getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity(name = "Booking") //enables JPA
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @Column(name = "route_id", nullable = false)
    private int routeId;

    @Column(name = "username", nullable = false)
    private String username;

}
