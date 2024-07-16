package com.interenting.repositories

import com.interenting.models.EcoPractice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EcoPracticeRepository : JpaRepository<EcoPractice, Long> {
    fun findByPropertyId(propertyId: Long): List<EcoPractice>?
}
