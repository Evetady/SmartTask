package com.example.SmartTask.repository

import com.example.SmartTask.model.Task
import com.example.SmartTask.model.TaskStatus
import com.example.SmartTask.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    fun findByUser(user: User): List<Task>
    fun findByUserAndStatus(user: User, status: TaskStatus): List<Task>
    fun deleteByUserAndId(user: User, id: Long)
}