package com.interenting.services.hedera

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.interenting.models.Booking
import com.interenting.models.BookingStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class SmartContractService : ISmartContractService {

    @Value("classpath:contracts/dist/BookingContract.json")
    lateinit var resourceFile: Resource

    override fun deployContract(booking: Booking): String {
        val mapper = jacksonObjectMapper()
        // Deploy smart contract logic
        // Replace with your actual smart contract deployment code

        // val abi = mapper.readValue(resourceFile)

        return "sampleContractId"
    }

    override fun executeContract(contractId: String, status: BookingStatus) {
        // Execute smart contract logic
    }
}
