package com.interenting.repositories

import com.interenting.models.EcoToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EcoTokenRepository : JpaRepository<EcoToken, Long> {
    fun findByOwnerId(ownerId: Long): EcoToken?

}