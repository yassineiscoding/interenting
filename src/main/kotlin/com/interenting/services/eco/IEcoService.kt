package com.interenting.services.eco

import com.interenting.models.EcoPractice
import com.interenting.models.EcoToken

interface IEcoService {

    fun getEcoStatus(propertyId: Long): List<EcoPractice>?

    fun submitEcoPractice(ecoPractice: EcoPractice)

    fun redeemEcoTokens(userId: Long, amount: Int)

    fun getEcoTokens(userId: Long): EcoToken
}
