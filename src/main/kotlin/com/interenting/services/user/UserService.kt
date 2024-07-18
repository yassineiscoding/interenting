package com.interenting.services.user

import com.interenting.models.User
import com.interenting.repositories.UserRepository
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class UserService(private val userRepository: UserRepository) : IUserService {

    override fun registerUser(user: User) {
        user.kycStatus = "PENDING"
        userRepository.save(user)
    }

    override fun loginUser(email: String, password: String): Long {
        val user = userRepository.findByEmail(email) ?: return -1
        return if (password == user.password) user.id else -1;
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

    override fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
}
