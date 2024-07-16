package com.interenting.services

import com.interenting.models.*
import com.interenting.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PropertyService @Autowired constructor(
    private val propertyRepository: PropertyRepository,
    private val ecoPracticeRepository: EcoPracticeRepository,
    private val userRepository: UserRepository,
    private val guardianService: GuardianService,
    private val hederaTokenService: HederaTokenService,
) {

    fun listProperties(): List<Property> {
        return propertyRepository.findAll()
    }

    fun getPropertyDetails(id: Long): Property? {
        return propertyRepository.findById(id).orElse(null)
    }

    fun tokenizeProperty(property: Property): String {
        val owner = userRepository.findById(property.ownerId).orElse(null)
            ?: throw IllegalArgumentException("Owner not found")

        val tokenId = hederaTokenService.createToken(property.name, property.totalShares)
        property.hederaTokenId = tokenId.toString()
        property.availableShares = (property.totalShares * 0.49).toInt()
        propertyRepository.save(property)

        return tokenId.toString()
    }

    fun updateEcoStatus(id: Long, ecoTier: EcoTier) {
    val property = propertyRepository.findById(id).orElse(null)
        ?: throw IllegalArgumentException("Property not found")

    property.ecoTier = ecoTier
    propertyRepository.save(property)

    val ecoPractices = ecoPracticeRepository.findByPropertyId(property.id)
    val ecoPractice = ecoPractices?.firstOrNull()
        ?: EcoPractice(propertyId = property.id, description = "Initial Practice", verificationStatus = VerificationStatus.PENDING)

    ecoPracticeRepository.save(ecoPractice)
    guardianService.verifyEcoPractice(ecoPractice.id, property.id)
}
}
