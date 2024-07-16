package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.AccountId
import com.hedera.hashgraph.sdk.TokenId

interface ITokenService {

    fun createToken(tokenName: String, totalSupply: Int): TokenId

    fun transferToken(
        tokenId: TokenId,
        fromAccountId: AccountId,
        toAccountId: AccountId,
        amount: Long
    )

    fun burnToken(tokenId: TokenId, amount: Long)

}
