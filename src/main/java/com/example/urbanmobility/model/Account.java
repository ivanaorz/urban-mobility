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

@Entity(name = "Account") //enables JPA


public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

            @Column(name = "username", nullable = false)
    private String username;

            @Column(name = "role", nullable = false)
    private String role;

            @Column(name = "phone", nullable = false)
    private String phone;

            @Column(name = "payment_info", nullable = false)
    private String paymentInfo;

            @Column(name = "payment_history", nullable = false)
    private int paymentHistory;

            @Column(name = "active_bookings", nullable = false)
    private String activeBookings;

            @Column(name = "is_payment_set", nullable = false)
    private boolean isPaymentSet;
}

