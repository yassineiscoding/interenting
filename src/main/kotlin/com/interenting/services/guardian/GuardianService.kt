package com.interenting.services.guardian

import org.springframework.stereotype.Service

@Service
class GuardianService : IGuardianService {

    override fun initiateCertificationPolicy(propertyId: Long) {
        // Initiate certification policy logic
    }

    override fun verifyEcoPractice(ecoPracticeId: Long, propertyId: Long) {
        // Verify eco practice logic
    }

    override fun conductAnnualAudit(propertyId: Long) {
        // Conduct annual audit logic
    }
}
