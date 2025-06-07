package com.example.SmartTask.service

import com.example.SmartTask.model.User
import com.example.SmartTask.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(username: String, password: String, email: String): User {
        if (userRepository.existsByUsername(username)) {
            throw RuntimeException("Пользователь с таким именем уже существует")
        }
        if (userRepository.existsByEmail(email)) {
            throw RuntimeException("Пользователь с таким email уже существует")
        }

        val user = User(
            _username = username,
            password = passwordEncoder.encode(password),
            email = email
        )
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email).orElse(null)
    }
}