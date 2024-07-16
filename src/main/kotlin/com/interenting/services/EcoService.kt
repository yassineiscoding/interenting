package com.interenting.services

import com.interenting.models.*
import com.interenting.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EcoService @Autowired constructor(
    private val ecoTokenRepository: EcoTokenRepository,
    private val ecoPracticeRepository: EcoPracticeRepository,
    private val guardianService: GuardianService
) {

    fun getEcoStatus(propertyId: Long): List<EcoPractice>? {
        val practices = ecoPracticeRepository.findByPropertyId(propertyId)
        return practices
    }

    fun submitEcoPractice(ecoPractice: EcoPractice) {
        ecoPractice.verificationStatus = VerificationStatus.PENDING
        ecoPracticeRepository.save(ecoPractice)
        guardianService.verifyEcoPractice(ecoPractice.id, ecoPractice.propertyId)
    }

    fun redeemEcoTokens(userId: Long, amount: Int) {
        val ecoToken = ecoTokenRepository.findByOwnerId(userId)
            ?: throw IllegalArgumentException("EcoToken not found")
        if (ecoToken.balance < amount) {
            throw IllegalArgumentException("Insufficient balance")
        }
        ecoToken.balance -= amount
        ecoTokenRepository.save(ecoToken)
    }

    fun getEcoTokens(userId: Long): EcoToken {
        return ecoTokenRepository.findByOwnerId(userId)
            ?: throw IllegalArgumentException("EcoToken not found")
    }
}
