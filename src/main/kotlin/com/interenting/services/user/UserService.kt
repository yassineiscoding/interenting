package com.interenting.services.user

import com.interenting.models.User
import com.interenting.repositories.UserRepository
import lombok.AllArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class UserService(private val userRepository: UserRepository) : IUserService {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun registerUser(user: User) {
        user.password = passwordEncoder.encode(user.password)
        user.kycStatus = "PENDING"
        userRepository.save(user)
    }

    override fun loginUser(email: String, password: String): Boolean {
        val user = userRepository.findByEmail(email) ?: return false
        return passwordEncoder.matches(password, user.password)
    }

    override fun updateProfile(id: Long, updatedUser: User) {
        val user = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User not found")
        user.name = updatedUser.name
        user.email = updatedUser.email
        userRepository.save(user)
    }

    override fun completeKYC(id: Long) {
        val user = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User not found")
        user.kycStatus = "APPROVED"
        userRepository.save(user)
    }
}
