package com.interenting.services.booking

import com.interenting.models.Booking
import com.interenting.models.BookingStatus

interface IBookingService {

    fun createBooking(booking: Booking)

    fun updateBookingStatus(id: Long, status: BookingStatus)

    fun getBookingDetails(id: Long): Booking?
}
