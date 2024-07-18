package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmartContractService : ISmartContractService {

    @Value("\${app.bookingContractId}")
    lateinit var bookingContractId: String

    @Value("\${app.accountId}")
    lateinit var operatorID: String

    @Value("\${app.adminPrivateKey}")
    lateinit var operatorKey: String

    override fun saveBookingOnChain(
        id: Long,
        stringObj: String,
        owner: AccountId
    ): String {

        val operatorId = AccountId.fromString(operatorID)

        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )

        val query =
            ContractCallQuery()
                .setContractId(ContractId.fromString(bookingContractId))
                .setGas(100_000_000)
                .setFunction(
                    "addBooking", ContractFunctionParameters()
                        .addUint256(id.toBigInteger())
                        .addString(stringObj)
                )

        val result =
            query?.execute(client)

        return result?.getUint256(0).toString()
    }

    override fun cancelBookingOnChain(stringObj: String, owner: AccountId) {

        val operatorId = AccountId.fromString(operatorID)

        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )

        val transaction: ContractExecuteTransaction? =
            ContractExecuteTransaction()
                .setContractId(ContractId.fromString(bookingContractId))
                .setGas(100_000_000)
                .setFunction(
                    "cancelBooking", ContractFunctionParameters()
                        .addString(stringObj)
                )
                .sign(operatorKey)

        val txResponse: TransactionResponse? =
            transaction?.execute(client)

        val receipt: TransactionReceipt? =
            txResponse?.getReceipt(client)

        val transactionStatus: Status? = receipt?.status

        println("Saving the booking transaction consensus status is $transactionStatus")
    }

    override fun checkInBooking(id: Long, owner: AccountId) {
        val operatorId = AccountId.fromString(operatorID)

        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )

        val transaction = ContractExecuteTransaction()
            .setContractId(ContractId.fromString(bookingContractId))
            .setGas(100_000_000)
            .setFunction(
                "checkIn", ContractFunctionParameters()
                    .addUint256(id.toBigInteger())
            )

        val txResponse = transaction.execute(client)

        val receipt = txResponse.getReceipt(client)

        println("Checkin receipt: $receipt")
    }

    override fun checkOutBooking(id: Long, owner: AccountId) {
        val operatorId = AccountId.fromString(operatorID)

        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )

        val transaction = ContractExecuteTransaction()
            .setContractId(ContractId.fromString(bookingContractId))
            .setGas(100_000_000)
            .setFunction(
                "checkOut", ContractFunctionParameters()
                    .addUint256(id.toBigInteger())
            )

        val txResponse = transaction.execute(client)

        val receipt = txResponse.getReceipt(client)

        println("Checkout receipt: $receipt")
    }

    override fun updateBooking(newHash: String, owner: AccountId, index: Long) {
        val operatorId = AccountId.fromString(operatorID)

        val operatorKey = PrivateKey.fromString(operatorKey)

        val client: Client = Client.forTestnet().setOperator(
            operatorId,
            operatorKey
        )

        val transaction = ContractExecuteTransaction()
            .setContractId(ContractId.fromString(bookingContractId))
            .setGas(100_000_000)
            .setFunction(
                "updateBooking", ContractFunctionParameters()
                    .addUint256(index.toBigInteger())
                    .addString(newHash)
            )

        val txResponse = transaction.execute(client)

        val receipt = txResponse.getReceipt(client)

        println("Update receipt: $receipt")
    }

}
