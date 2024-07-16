package com.interenting.services.eco

import com.interenting.models.EcoPractice
import com.interenting.models.EcoToken
import com.interenting.models.VerificationStatus
import com.interenting.repositories.EcoPracticeRepository
import com.interenting.repositories.EcoTokenRepository
import com.interenting.services.guardian.IGuardianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EcoService @Autowired constructor(
    private val ecoTokenRepository: EcoTokenRepository,
    private val ecoPracticeRepository: EcoPracticeRepository,
    private val guardianService: IGuardianService
) : IEcoService {

    override fun getEcoStatus(propertyId: Long): List<EcoPractice>? {
        val practices = ecoPracticeRepository.findByPropertyId(propertyId)
        return practices
    }

    override fun submitEcoPractice(ecoPractice: EcoPractice) {
        ecoPractice.verificationStatus = VerificationStatus.PENDING
        ecoPracticeRepository.save(ecoPractice)
        guardianService.verifyEcoPractice(
            ecoPractice.id,
            ecoPractice.propertyId
        )
    }

    override fun redeemEcoTokens(userId: Long, amount: Int) {
        val ecoToken = ecoTokenRepository.findByOwnerId(userId)
            ?: throw IllegalArgumentException("EcoToken not found")
        if (ecoToken.balance < amount) {
            throw IllegalArgumentException("Insufficient balance")
        }
        ecoToken.balance -= amount
        ecoTokenRepository.save(ecoToken)
    }

    override fun getEcoTokens(userId: Long): EcoToken {
        return ecoTokenRepository.findByOwnerId(userId)
            ?: throw IllegalArgumentException("EcoToken not found")
    }
}
