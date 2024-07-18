package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.AccountId

interface ISmartContractService {
    fun saveBookingOnChain(
        id: Long,
        stringObj: String,
        owner: AccountId
    ): String

    fun cancelBookingOnChain(stringObj: String, owner: AccountId)
    fun checkInBooking(id: Long, owner: AccountId)
    fun checkOutBooking(id: Long, owner: AccountId)
    fun updateBooking(newHash: String, owner: AccountId, index: Long)
}
