package com.interenting.services.property

import com.interenting.models.EcoTier
import com.interenting.models.Property

interface IPropertyService {

    fun listProperties(): List<Property>

    fun getPropertyDetails(id: Long): Property?

    fun tokenizeProperty(property: Property): String

    fun updateEcoStatus(id: Long, ecoTier: EcoTier)
}
