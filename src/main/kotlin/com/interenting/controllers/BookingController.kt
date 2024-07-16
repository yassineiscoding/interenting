package com.interenting.controllers

import com.interenting.models.BookingStatus

import com.interenting.models.Booking
import com.interenting.services.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingController @Autowired constructor(private val bookingService: BookingService) {

    @PostMapping
    fun createBooking(@RequestBody booking: Booking): ResponseEntity<String> {
        bookingService.createBooking(booking)
        return ResponseEntity.ok("Booking created successfully")
    }

    @PutMapping("/{id}/status")
    fun updateBookingStatus(@PathVariable id: Long, @RequestParam status: BookingStatus): ResponseEntity<String> {
        bookingService.updateBookingStatus(id, status)
        return ResponseEntity.ok("Booking status updated successfully")
    }

    @GetMapping("/{id}")
    fun getBookingDetails(@PathVariable id: Long): ResponseEntity<Booking?> {
        val booking = bookingService.getBookingDetails(id)
        return if (booking != null) {
            ResponseEntity.ok(booking)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}