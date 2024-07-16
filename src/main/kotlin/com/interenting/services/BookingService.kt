package com.interenting.services

import com.interenting.models.*
import com.interenting.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService @Autowired constructor(
    private val bookingRepository: BookingRepository,
    private val smartContractService: SmartContractService
) {

    fun createBooking(booking: Booking) {
        booking.status = BookingStatus.PENDING
        val contractId = smartContractService.deployContract(booking)
        booking.smartContractId = contractId
        bookingRepository.save(booking)
    }

    fun updateBookingStatus(id: Long, status: BookingStatus) {
        val booking = bookingRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Booking not found")
        booking.status = status
        smartContractService.executeContract(booking.smartContractId, status)
        bookingRepository.save(booking)
    }

    fun getBookingDetails(id: Long): Booking? {
        return bookingRepository.findById(id).orElse(null)
    }
}
