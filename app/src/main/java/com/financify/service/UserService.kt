package com.financify.service

import com.financify.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @PUT("users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body user: User
    ): User
}