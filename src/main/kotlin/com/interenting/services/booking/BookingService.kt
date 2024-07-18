package com.interenting.services.booking

import com.hedera.hashgraph.sdk.*
import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import com.interenting.payload.BookingPaymentDetails
import com.interenting.payload.GenericResp
import com.interenting.repositories.BookingRepository
import com.interenting.services.hedera.ISmartContractService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.Long.parseLong
import java.time.LocalDateTime


@Service
@AllArgsConstructor
class BookingService(
    private val bookingRepository: BookingRepository,
    private val smartContractService: ISmartContractService
) : IBookingService {

    @Value("\${app.bookingContractId}")
    lateinit var bookingContractID: String

    @Value("\${app.accountId}")
    lateinit var operatorID: String

    @Value("\${app.adminPrivateKey}")
    lateinit var operatorKey: String

    override fun createBooking(
        booking: Booking,
        accountId: String
    ): ResponseEntity<GenericResp> {

        booking.status = BookingStatus.PENDING
        booking.smartContractId = bookingContractID

        val saved = bookingRepository.save(booking)

        val bookingOnChainId = smartContractService.saveBookingOnChain(
            booking.id,
            Json.encodeToString(saved),
            AccountId.fromString(accountId)
        )

        saved.smartContractId = bookingOnChainId

        bookingRepository.save(booking)

        return ResponseEntity.ok(GenericResp(message = "Booking created successfully"))
    }

    override fun updateBookingStatus(
        id: Long,
        status: BookingStatus
    ): ResponseEntity<GenericResp> {
        val booking = bookingRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Booking not found")

        booking.status = status
        val updated = bookingRepository.save(booking)

        smartContractService.updateBooking(
            Json.encodeToString(updated),
            AccountId.fromString(operatorID),
            parseLong(booking.smartContractId)
        )

        return ResponseEntity.ok(GenericResp(message = "Booking updated successfully"))
    }

    override fun getBookingDetails(id: Long): Booking? {
        return bookingRepository.findById(id).orElse(null)
    }

    override fun checkoutBooking(bookingId: Long): ResponseEntity<GenericResp> {
        return ResponseEntity.ok(GenericResp(message = "Booking checked out"))
    }

    override fun checkInBooking(bookingId: Long): ResponseEntity<GenericResp> {
        return ResponseEntity.ok(GenericResp(message = "Booking checked in"))

    }

    override fun cancelBooking(bookingId: Long): ResponseEntity<GenericResp> {
        return ResponseEntity.ok(GenericResp(message = "Booking cancelled"))

    }

    override fun payForBooking(paymentDetails: BookingPaymentDetails): ResponseEntity<GenericResp> {
        val operatorId = AccountId.fromString(operatorID)
        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )
        val recipientId = AccountId.fromString(paymentDetails.receiverAccId)
        val senderId = AccountId.fromString(
            paymentDetails.senderAccId
        )

        val amount = Hbar.fromTinybars(paymentDetails.amount)

        val transactionResponse: TransactionResponse =
            TransferTransaction()
                .addHbarTransfer(senderId, amount.negated())
                .addHbarTransfer(recipientId, amount)
                .setTransactionMemo("payment of booking")
                .execute(client)

        println("transaction ID: $transactionResponse")

        val record: TransactionRecord = transactionResponse.getRecord(client)

        println("Transaction memo if successful: ${record.transactionMemo}")

        val booking =
            bookingRepository.findById(paymentDetails.bId).orElse(null)
                ?: throw IllegalArgumentException("Booking not found")

        booking.checkIn = LocalDateTime.now()
        booking.status = BookingStatus.CONFIRMED

        smartContractService.checkInBooking(
            booking.id,
            senderId
        )

        return ResponseEntity.ok(GenericResp(message = "Payment successful"))
    }
}
