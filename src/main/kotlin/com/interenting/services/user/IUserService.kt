package com.interenting.services.user

import com.interenting.models.User

interface IUserService {

    fun registerUser(user: User)

    fun loginUser(email: String, password: String): Boolean

    fun updateProfile(id: Long, updatedUser: User)

    fun completeKYC(id: Long)
}
