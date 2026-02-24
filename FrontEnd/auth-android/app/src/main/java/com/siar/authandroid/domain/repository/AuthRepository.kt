package com.siar.authandroid.domain.repository

import com.siar.authandroid.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
}
