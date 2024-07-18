package com.interenting.models

import com.interenting.config.DateSerializer
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import lombok.EqualsAndHashCode
import java.time.LocalDateTime

@Entity
@EqualsAndHashCode
@Serializable
class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val propertyId: Long,

    var guestId: Long,

    @NotNull
    @Serializable(with = DateSerializer::class)
    var checkIn: LocalDateTime,

    @NotNull
    @Serializable(with = DateSerializer::class)
    var checkOut: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: BookingStatus = BookingStatus.PENDING,

    @NotBlank
    var smartContractId: String
)
