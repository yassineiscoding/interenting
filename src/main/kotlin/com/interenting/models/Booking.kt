package com.interenting.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val propertyId: Long,

    val guestId: Long,

    @NotNull
    val checkIn: LocalDateTime,

    @NotNull
    val checkOut: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: BookingStatus = BookingStatus.PENDING,

    @NotBlank
    var smartContractId: String
)