package com.interenting.controllers

import com.interenting.models.User
import com.interenting.payload.GenericResp
import com.interenting.services.user.IUserService
import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController(private val userService: IUserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): ResponseEntity<GenericResp> {
        userService.registerUser(user)
        return ResponseEntity.ok(GenericResp(message = "User registered successfully"))
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<GenericResp> {
        val loginUser = userService.loginUser(email, password)
        return if (loginUser != -1L) {
            ResponseEntity.ok(GenericResp(message = "Login successful, id = $loginUser"))
        } else {
            ResponseEntity.status(401)
                .body(GenericResp(error = "Invalid credentials"))
        }
    }

    @PutMapping("/{id}/profile")
    fun updateProfile(
        @PathVariable id: Long,
        @RequestBody user: User
    ): ResponseEntity<GenericResp> {
        userService.updateProfile(id, user)
        return ResponseEntity.ok(GenericResp(message = "Profile updated successfully"))
    }

    @PostMapping("/{id}/kyc")
    fun completeKYC(@PathVariable id: Long): ResponseEntity<GenericResp> {
        userService.completeKYC(id)
        return ResponseEntity.ok(GenericResp(message = "KYC completed successfully"))
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<Any> {
        val user = userService.getUserById(id)

        return if (user == null) ResponseEntity(
            GenericResp(error = "Not found!"),
            HttpStatus.NOT_FOUND
        )
        else ResponseEntity.ok(user)
    }
}
