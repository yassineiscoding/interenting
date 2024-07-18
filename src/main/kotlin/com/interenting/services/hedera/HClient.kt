package com.interenting.services.hedera

import com.hedera.hashgraph.sdk.AccountId
import com.hedera.hashgraph.sdk.Client
import com.hedera.hashgraph.sdk.PrivateKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
object HClient {

    @Value("\${app.accountId}")
    lateinit var operatorID: String

    @Value("\${app.adminPrivateKey}")
    lateinit var operatorKey: String

    val OPERATOR_ID: AccountId = AccountId.fromString(operatorID)
    val OPERATOR_KEY: PrivateKey = PrivateKey.fromString(operatorKey)

    val client = Client.forTestnet().setOperator(
        AccountId.fromString(operatorID),
        PrivateKey.fromString(operatorKey)
    )
}
