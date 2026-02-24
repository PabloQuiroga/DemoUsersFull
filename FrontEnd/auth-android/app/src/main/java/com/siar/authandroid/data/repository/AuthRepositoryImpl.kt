package com.siar.authandroid.data.repository

import com.siar.authandroid.domain.model.User
import com.siar.authandroid.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        delay(1000) // Simular delay de red
        return if (email == "test@test.com" && password == "1234") {
            Result.success(User("1", email, "fake-jwt-token"))
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }

    override suspend fun register(email: String, password: String): Result<User> {
        delay(1000)
        return Result.success(User("2", email, "fake-jwt-token-new"))
    }
}
