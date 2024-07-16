package com.interenting.services.rating

import com.interenting.models.Rating
import com.interenting.repositories.RatingRepository
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class RatingService(private val ratingRepository: RatingRepository) :
    IRatingService {

    override fun submitRating(rating: Rating) {
        ratingRepository.save(rating)
    }

    override fun getRatings(propertyId: Long): List<Rating> {
        return ratingRepository.findByPropertyId(propertyId)
    }

    override fun calculateAverageRating(propertyId: Long): Double {
        val ratings = ratingRepository.findByPropertyId(propertyId)
        return if (ratings.isNotEmpty()) {
            ratings.map { it.score }.average()
        } else {
            0.0
        }
    }
}
