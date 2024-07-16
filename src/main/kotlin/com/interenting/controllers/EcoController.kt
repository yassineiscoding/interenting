package com.interenting.controllers

import com.interenting.models.EcoPractice
import com.interenting.models.EcoToken
import com.interenting.services.eco.IEcoService
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/eco")
@AllArgsConstructor
class EcoController(private val ecoService: IEcoService) {

    @GetMapping("/status")
    fun getEcoStatus(@RequestParam propertyId: Long): ResponseEntity<List<EcoPractice>> {
        return ResponseEntity.ok(ecoService.getEcoStatus(propertyId))
    }

    @PostMapping("/practice")
    fun submitEcoPractice(@RequestBody ecoPractice: EcoPractice): ResponseEntity<String> {
        ecoService.submitEcoPractice(ecoPractice)
        return ResponseEntity.ok("Eco practice submitted successfully")
    }

    @PostMapping("/redeem")
    fun redeemEcoTokens(
        @RequestParam userId: Long,
        @RequestParam amount: Int
    ): ResponseEntity<String> {
        ecoService.redeemEcoTokens(userId, amount)
        return ResponseEntity.ok("Eco tokens redeemed successfully")
    }

    @GetMapping("/tokens")
    fun getEcoTokens(@RequestParam userId: Long): ResponseEntity<EcoToken> {
        return ResponseEntity.ok(ecoService.getEcoTokens(userId))
    }
}
