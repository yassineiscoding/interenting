package com.interenting.controllers

import com.interenting.models.User
import com.interenting.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): ResponseEntity<String> {
        userService.registerUser(user)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun loginUser(@RequestParam email: String, @RequestParam password: String): ResponseEntity<String> {
        val result = userService.loginUser(email, password)
        return if (result) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }

    @PutMapping("/{id}/profile")
    fun updateProfile(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<String> {
        userService.updateProfile(id, user)
        return ResponseEntity.ok("Profile updated successfully")
    }

    @PostMapping("/{id}/kyc")
    fun completeKYC(@PathVariable id: Long): ResponseEntity<String> {
        userService.completeKYC(id)
        return ResponseEntity.ok("KYC completed successfully")
    }
}
