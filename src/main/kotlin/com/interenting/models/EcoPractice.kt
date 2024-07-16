package com.interenting.models

import jakarta.persistence.*

@Entity
data class EcoPractice(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val propertyId: Long,

    val description: String,

    @Enumerated(EnumType.STRING)
    var verificationStatus: VerificationStatus = VerificationStatus.PENDING
)