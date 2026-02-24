package com.siar.authandroid.domain.usecase

import com.siar.authandroid.domain.model.User
import com.siar.authandroid.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Aquí podrías agregar lógica de validación de negocio adicional
        return repository.login(email, password)
    }
}
