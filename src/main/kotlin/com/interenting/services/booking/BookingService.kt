package com.interenting.services.booking

import com.hedera.hashgraph.sdk.AccountId
import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import com.interenting.payload.GenericResp
import com.interenting.repositories.BookingRepository
import com.interenting.services.hedera.ISmartContractService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class BookingService(
    private val bookingRepository: BookingRepository,
    private val smartContractService: ISmartContractService
) : IBookingService {

    override fun createBooking(
        booking: Booking,
        accountId: String
    ): ResponseEntity<GenericResp> {

        booking.status = BookingStatus.PENDING

        val saved = bookingRepository.save(booking)

        smartContractService.saveBookingOnChain(
            Json.encodeToString(saved),
            AccountId.fromString(accountId)
        )

        return ResponseEntity.ok(GenericResp(message = "Booking created successfully"))
    }

    override fun updateBookingStatus(
        id: Long,
        status: BookingStatus
    ): ResponseEntity<GenericResp> {
        val booking = bookingRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Booking not found")

        booking.status = status
        bookingRepository.save(booking)

        return ResponseEntity.ok(GenericResp(message = "Booking updated successfully"))
    }

    override fun getBookingDetails(id: Long): Booking? {
        return bookingRepository.findById(id).orElse(null)
    }
}
