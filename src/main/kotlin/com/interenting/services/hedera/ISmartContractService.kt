package com.interenting.services.hedera

import com.interenting.models.Booking
import com.interenting.models.BookingStatus

interface ISmartContractService {

    fun deployContract(booking: Booking): String

    fun executeContract(contractId: String, status: BookingStatus)
}
