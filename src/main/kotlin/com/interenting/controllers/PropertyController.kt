package com.interenting.controllers

import com.interenting.models.EcoTier
import com.interenting.models.Property
import com.interenting.services.PropertyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/properties")
class PropertyController @Autowired constructor(private val propertyService: PropertyService) {

    @GetMapping
    fun listProperties(): ResponseEntity<List<Property>> {
        return ResponseEntity.ok(propertyService.listProperties())
    }

    @GetMapping("/{id}")
    fun getPropertyDetails(@PathVariable id: Long): ResponseEntity<Property?> {
        val property = propertyService.getPropertyDetails(id)
        return if (property != null) {
            ResponseEntity.ok(property)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/tokenize")
    fun tokenizeProperty(@RequestBody property: Property): ResponseEntity<String> {
        propertyService.tokenizeProperty(property)
        return ResponseEntity.ok("Property tokenized successfully")
    }

    @PutMapping("/{id}/ecoStatus")
    fun updateEcoStatus(@PathVariable id: Long, @RequestParam ecoTier: EcoTier): ResponseEntity<String> {
        propertyService.updateEcoStatus(id, ecoTier)
        return ResponseEntity.ok("Eco status updated successfully")
    }
}
