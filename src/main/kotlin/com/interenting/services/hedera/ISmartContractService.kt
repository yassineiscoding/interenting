package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.AccountId

interface ISmartContractService {

    fun saveBookingOnChain(stringObj: String, owner: AccountId)
    fun cancelBookingOnChain(owner: AccountId)


}
