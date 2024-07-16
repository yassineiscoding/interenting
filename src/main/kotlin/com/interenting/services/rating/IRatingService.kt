package com.interenting.services.rating

import com.interenting.models.Rating

interface IRatingService {

    fun submitRating(rating: Rating)

    fun getRatings(propertyId: Long): List<Rating>

    fun calculateAverageRating(propertyId: Long): Double
}
