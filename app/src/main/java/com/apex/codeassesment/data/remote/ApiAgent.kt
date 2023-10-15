package com.apex.codeassesment.data.remote
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiAgent {

    @GET("api")
    suspend fun loadUser(): Response<UserResponse>

    @GET("api?results=10")
    suspend fun loadUsers(): Response<UserResponse>

    companion object {
        const val BASE_API_URL = "https://randomuser.me/"
    }
}