package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmartContractService : ISmartContractService {

    @Value("\${app.bookingContractId}")
    lateinit var bookingContractId: String

    override fun saveBookingOnChain(stringObj: String, owner: AccountId) {
        //Create the transaction
        val transaction: ContractExecuteTransaction? =
            ContractExecuteTransaction()
                .setContractId(ContractId.fromString(bookingContractId))
                .setGas(100_000_000)
                .setFunction(
                    "addBooking", ContractFunctionParameters()
                        .addString("hello from hedera again!")
                )

        //Sign with the client operator private key to pay for the transaction and submit the query to a Hedera network
        val txResponse: TransactionResponse? =
            transaction?.execute(HClient.client)

        //Request the receipt of the transaction
        val receipt: TransactionReceipt? =
            txResponse?.getReceipt(HClient.client)

        //Get the transaction consensus status
        val transactionStatus: Status? = receipt?.status

        println("Saving the booking transaction consensus status is $transactionStatus")

    }

    override fun cancelBookingOnChain(owner: AccountId) {
        //Create the transaction
        val transaction: ContractExecuteTransaction? =
            ContractExecuteTransaction()
                .setContractId(ContractId.fromString(bookingContractId))
                .setGas(100_000_000)
                .setFunction(
                    "cancelBooking", ContractFunctionParameters()
                        .addString("hello from hedera again!")
                )

        //Sign with the client operator private key to pay for the transaction and submit the query to a Hedera network
        val txResponse: TransactionResponse? =
            transaction?.execute(HClient.client)

        //Request the receipt of the transaction
        val receipt: TransactionReceipt? =
            txResponse?.getReceipt(HClient.client)

        //Get the transaction consensus status
        val transactionStatus: Status? = receipt?.status

        println("Saving the booking transaction consensus status is $transactionStatus")
    }

}
