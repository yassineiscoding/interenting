package com.interenting.services.booking

import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import com.interenting.repositories.BookingRepository
import com.interenting.services.hedera.ISmartContractService
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class BookingService(
    private val bookingRepository: BookingRepository,
    private val smartContractService: ISmartContractService
) : IBookingService {

    override fun createBooking(booking: Booking) {
        booking.status = BookingStatus.PENDING
        val contractId = smartContractService.deployContract(booking)
        booking.smartContractId = contractId
        bookingRepository.save(booking)
    }

    override fun updateBookingStatus(id: Long, status: BookingStatus) {
        val booking = bookingRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Booking not found")
        booking.status = status
        smartContractService.executeContract(booking.smartContractId, status)
        bookingRepository.save(booking)
    }

    override fun getBookingDetails(id: Long): Booking? {
        return bookingRepository.findById(id).orElse(null)
    }
}
