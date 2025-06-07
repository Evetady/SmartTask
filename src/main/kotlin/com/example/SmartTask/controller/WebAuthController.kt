package com.example.SmartTask.controller

import com.example.SmartTask.dto.LoginRequest
import com.example.SmartTask.dto.RegisterRequest
import com.example.SmartTask.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class WebAuthController(private val authService: AuthService) {

    @GetMapping("/login")
    fun loginPage(): String = "login"

    @GetMapping("/register")
    fun registerPage(): String = "register"

    @PostMapping("/login")
    fun login(
        request: LoginRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val response = authService.login(request)
            // Здесь можно сохранить токен в cookie или session
            "redirect:/tasks"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Неверное имя пользователя или пароль")
            "redirect:/login"
        }
    }

    @PostMapping("/register")
    fun register(
        request: RegisterRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val response = authService.register(request)
            redirectAttributes.addFlashAttribute("success", "Регистрация успешна! Теперь вы можете войти.")
            "redirect:/login"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", e.message)
            "redirect:/register"
        }
    }
} 