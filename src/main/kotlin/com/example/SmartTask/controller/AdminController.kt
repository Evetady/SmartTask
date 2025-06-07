package com.example.SmartTask.controller

import com.example.SmartTask.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController(
    private val userRepository: UserRepository
) {
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    fun listUsers(model: Model): String {
        val users = userRepository.findAll()
        model.addAttribute("users", users)
        return "admin/users"
    }
} 