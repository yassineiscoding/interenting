package com.interenting.services.property

import com.interenting.models.EcoPractice
import com.interenting.models.EcoTier
import com.interenting.models.Property
import com.interenting.models.VerificationStatus
import com.interenting.repositories.EcoPracticeRepository
import com.interenting.repositories.PropertyRepository
import com.interenting.repositories.UserRepository
import com.interenting.services.guardian.IGuardianService
import com.interenting.services.hedera.ITokenService
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val ecoPracticeRepository: EcoPracticeRepository,
    private val userRepository: UserRepository,
    private val guardianService: IGuardianService,
    private val hederaTokenService: ITokenService,
) : IPropertyService {

    override fun listProperties(): List<Property> {
        return propertyRepository.findAll()
    }

    override fun getPropertyDetails(id: Long): Property? {
        return propertyRepository.findById(id).orElse(null)
    }

    override fun tokenizeProperty(property: Property): String {
        val owner = userRepository.findById(property.ownerId).orElse(null)
            ?: throw IllegalArgumentException("Owner not found")

        val tokenId =
            hederaTokenService.createToken(property.name, property.totalShares)
        property.hederaTokenId = tokenId.toString()
        property.availableShares = (property.totalShares * 0.49).toInt()
        propertyRepository.save(property)

        return tokenId.toString()
    }

    override fun updateEcoStatus(id: Long, ecoTier: EcoTier) {
        val property = propertyRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Property not found")

        property.ecoTier = ecoTier
        propertyRepository.save(property)

        val ecoPractices = ecoPracticeRepository.findByPropertyId(property.id)
        val ecoPractice = ecoPractices?.firstOrNull()
            ?: EcoPractice(
                propertyId = property.id,
                description = "Initial Practice",
                verificationStatus = VerificationStatus.PENDING
            )

        ecoPracticeRepository.save(ecoPractice)
        guardianService.verifyEcoPractice(ecoPractice.id, property.id)
    }
}
