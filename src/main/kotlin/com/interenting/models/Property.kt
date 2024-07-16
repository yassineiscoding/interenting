package com.interenting.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank


@Entity
data class Property(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    val name: String,

    val description: String,

    @NotBlank
    val location: String,

    val totalShares: Int,

    val pricePerShare: Double,

    var availableShares: Int,

    @NotBlank
    var hederaTokenId: String,

    val ownerId: Long,

    @Enumerated(EnumType.STRING)
    var ecoTier: EcoTier = EcoTier.BRONZE,

    var ecoScore: Int = 0
)