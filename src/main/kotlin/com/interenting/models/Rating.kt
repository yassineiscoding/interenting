package com.interenting.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
//import javax.validation.constraints.*

@Entity
data class Rating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val propertyId: Long,

    val userId: Long,

//    @Min(0) @Max(5)
    val score: Double,

    val review: String,

    val timestamp: LocalDateTime = LocalDateTime.now()
)