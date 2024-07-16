package com.interenting.models

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "\"user\"")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotBlank
    var name: String,

    @Email @NotBlank
    var email: String,

    @NotBlank
    var password: String,

    @NotBlank
    val hederaAccountId: String,

    @NotBlank
    var kycStatus: String,

    val ecoScore: Int = 0
)