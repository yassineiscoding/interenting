package com.interenting.controllers

import com.interenting.models.Rating
import com.interenting.services.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ratings")
class RatingController @Autowired constructor(private val ratingService: RatingService) {

    @PostMapping
    fun submitRating(@RequestBody rating: Rating): ResponseEntity<String> {
        ratingService.submitRating(rating)
        return ResponseEntity.ok("Rating submitted successfully")
    }

    @GetMapping
    fun getRatings(@RequestParam propertyId: Long): ResponseEntity<List<Rating>> {
        return ResponseEntity.ok(ratingService.getRatings(propertyId))
    }

    @GetMapping("/{propertyId}/average")
    fun calculateAverageRating(@PathVariable propertyId: Long): ResponseEntity<Double> {
        return ResponseEntity.ok(ratingService.calculateAverageRating(propertyId))
    }
}
