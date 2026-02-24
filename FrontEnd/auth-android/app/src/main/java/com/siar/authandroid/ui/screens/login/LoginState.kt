package com.siar.authandroid.ui.screens.login

import com.siar.authandroid.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)