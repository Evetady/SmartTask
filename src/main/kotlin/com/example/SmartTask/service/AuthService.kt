package com.example.SmartTask.service

import com.example.SmartTask.dto.AuthResponse
import com.example.SmartTask.dto.LoginRequest
import com.example.SmartTask.dto.RegisterRequest
import com.example.SmartTask.model.User
import com.example.SmartTask.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw RuntimeException("Пользователь с таким именем уже существует")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw RuntimeException("Пользователь с таким email уже существует")
        }

        val user = User(
            _username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email
        )
        userRepository.save(user)

        val token = jwtService.generateToken(user)
        return AuthResponse(token, user.getUsername())
    }

    fun login(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { RuntimeException("Пользователь не найден") }
        
        val token = jwtService.generateToken(user)
        return AuthResponse(token, user.getUsername())
    }
}