package com.interenting.controllers

import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import com.interenting.payload.BookingPaymentDetails
import com.interenting.payload.GenericResp
import com.interenting.services.booking.IBookingService
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
class BookingController(private val bookingService: IBookingService) {

    @PostMapping
    fun createBooking(
        @RequestBody booking: Booking,
        ownerAccId: String
    ): ResponseEntity<String> {
        bookingService.createBooking(booking, ownerAccId)
        return ResponseEntity.ok("Booking created successfully")
    }

    @PutMapping("/{id}/status")
    fun updateBookingStatus(
        @PathVariable id: Long,
        @RequestParam status: BookingStatus
    ): ResponseEntity<String> {
        bookingService.updateBookingStatus(id, status)
        return ResponseEntity.ok("Booking status updated successfully")
    }

    @PutMapping("/{id}/cancel")
    fun cancelBooking(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        bookingService.cancelBooking(id)
        return ResponseEntity.ok("Booking cancelled")
    }

    @PutMapping("/{id}/checkout")
    fun checkoutBooking(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        bookingService.checkoutBooking(id)
        return ResponseEntity.ok("Booking cancelled")
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

    @PostMapping("/pay/{bookingId}")
    fun payBooking(
        @RequestBody paymentDetails: BookingPaymentDetails,
        @PathVariable bookingId: Long
    ): ResponseEntity<GenericResp> {
        return bookingService.payForBooking(paymentDetails)
    }
}
