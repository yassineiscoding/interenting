package com.interenting.services

import com.interenting.models.Rating
import com.interenting.repositories.RatingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatingService @Autowired constructor(private val ratingRepository: RatingRepository) {

    fun submitRating(rating: Rating) {
        ratingRepository.save(rating)
    }

    fun getRatings(propertyId: Long): List<Rating> {
        return ratingRepository.findByPropertyId(propertyId)
    }

    fun calculateAverageRating(propertyId: Long): Double {
        val ratings = ratingRepository.findByPropertyId(propertyId)
        return if (ratings.isNotEmpty()) {
            ratings.map { it.score }.average()
        } else {
            0.0
        }
    }
}
