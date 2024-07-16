package com.interenting.repositories

import com.interenting.models.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository : JpaRepository<Rating, Long>{
    fun findByPropertyId(propertyId: Long): List<Rating>
}