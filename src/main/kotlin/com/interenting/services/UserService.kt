package com.interenting.services

import com.interenting.models.User
import com.interenting.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(user: User) {
        user.password = passwordEncoder.encode(user.password)
        user.kycStatus = "PENDING"
        userRepository.save(user)
    }

    fun loginUser(email: String, password: String): Boolean {
        val user = userRepository.findByEmail(email) ?: return false
        return passwordEncoder.matches(password, user.password)
    }

    fun updateProfile(id: Long, updatedUser: User) {
        val user = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User not found")
        user.name = updatedUser.name
        user.email = updatedUser.email
        userRepository.save(user)
    }

    fun completeKYC(id: Long) {
        val user = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User not found")
        user.kycStatus = "APPROVED"
        userRepository.save(user)
    }
}
