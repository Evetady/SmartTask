package com.example.SmartTask.controller

import com.example.SmartTask.model.TaskStatus
import com.example.SmartTask.model.User
import com.example.SmartTask.service.TaskService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDateTime

@Controller
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping
    fun tasksPage(
        @AuthenticationPrincipal user: User,
        model: Model
    ): String {
        model.addAttribute("username", user.username)
        model.addAttribute("tasks", taskService.findByUser(user))
        return "tasks"
    }

    @PostMapping
    fun createTask(
        @AuthenticationPrincipal user: User,
        @RequestParam title: String,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) dueDate: LocalDateTime?,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            taskService.createTask(user, title, description, dueDate)
            redirectAttributes.addFlashAttribute("success", "Задача успешно создана")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании задачи: ${e.message}")
        }
        return "redirect:/tasks"
    }

    @PostMapping("/{id}/complete")
    fun completeTask(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            taskService.updateTaskStatus(user, id, TaskStatus.COMPLETED)
            redirectAttributes.addFlashAttribute("success", "Задача отмечена как выполненная")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении задачи: ${e.message}")
        }
        return "redirect:/tasks"
    }

    @PostMapping("/{id}/delete")
    fun deleteTask(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            taskService.deleteTask(user, id)
            redirectAttributes.addFlashAttribute("success", "Задача удалена")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении задачи: ${e.message}")
        }
        return "redirect:/tasks"
    }
}