package com.interenting.services.booking

import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import com.interenting.payload.GenericResp
import org.springframework.http.ResponseEntity

interface IBookingService {

    fun createBooking(booking: Booking, accountId: String): ResponseEntity<GenericResp>

    fun updateBookingStatus(id: Long, status: BookingStatus): ResponseEntity<GenericResp>

    fun getBookingDetails(id: Long): Booking?
}
