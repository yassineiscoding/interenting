package com.interenting.controllers

import com.interenting.models.User
import com.interenting.services.user.IUserService
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController(private val userService: IUserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): ResponseEntity<String> {
        userService.registerUser(user)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        val result = userService.loginUser(email, password)
        return if (result) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }

    @PutMapping("/{id}/profile")
    fun updateProfile(
        @PathVariable id: Long,
        @RequestBody user: User
    ): ResponseEntity<String> {
        userService.updateProfile(id, user)
        return ResponseEntity.ok("Profile updated successfully")
    }

    @PostMapping("/{id}/kyc")
    fun completeKYC(@PathVariable id: Long): ResponseEntity<String> {
        userService.completeKYC(id)
        return ResponseEntity.ok("KYC completed successfully")
    }
}
