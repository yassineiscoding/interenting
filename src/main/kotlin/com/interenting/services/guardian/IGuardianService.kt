package com.interenting.services.guardian

interface IGuardianService {

    fun initiateCertificationPolicy(propertyId: Long)

    fun verifyEcoPractice(ecoPracticeId: Long, propertyId: Long)

    fun conductAnnualAudit(propertyId: Long)
}
