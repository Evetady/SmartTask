package com.example.SmartTask.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "Имя пользователя не может быть пустым")
    val username: String,

    @field:NotBlank(message = "Пароль не может быть пустым")
    val password: String
)

data class RegisterRequest(
    @field:NotBlank(message = "Имя пользователя не может быть пустым")
    @field:Size(min = 3, max = 50, message = "Имя пользователя должно быть от 3 до 50 символов")
    val username: String,

    @field:NotBlank(message = "Пароль не может быть пустым")
    @field:Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    val password: String,

    @field:NotBlank(message = "Email не может быть пустым")
    @field:Email(message = "Некорректный формат email")
    val email: String
)

data class AuthResponse(
    val token: String,
    val username: String
) 