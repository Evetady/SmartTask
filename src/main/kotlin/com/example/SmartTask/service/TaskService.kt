package com.example.SmartTask.service

import com.example.SmartTask.model.Task
import com.example.SmartTask.model.TaskStatus
import com.example.SmartTask.model.User
import com.example.SmartTask.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {
    fun findByUser(user: User): List<Task> = taskRepository.findByUser(user)

    fun findByUserAndStatus(user: User, status: TaskStatus): List<Task> =
        taskRepository.findByUserAndStatus(user, status)

    @Transactional
    fun createTask(user: User, title: String, description: String?, dueDate: LocalDateTime?): Task {
        val task = Task(
            title = title,
            description = description,
            dueDate = dueDate,
            user = user
        )
        return taskRepository.save(task)
    }

    @Transactional
    fun updateTaskStatus(user: User, taskId: Long, newStatus: TaskStatus): Task {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NoSuchElementException("Задача не найдена") }
        
        if (task.user.id != user.id) {
            throw SecurityException("Нет доступа к этой задаче")
        }
        
        task.status = newStatus
        return taskRepository.save(task)
    }

    @Transactional
    fun deleteTask(user: User, taskId: Long) {
        taskRepository.deleteByUserAndId(user, taskId)
    }
}
