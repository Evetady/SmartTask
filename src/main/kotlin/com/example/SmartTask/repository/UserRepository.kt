package com.example.SmartTask.repository

import com.example.SmartTask.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u._username = :username")
    fun findByUsername(@Param("username") username: String): Optional<User>

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u._username = :username")
    fun existsByUsername(@Param("username") username: String): Boolean

    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<User>

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    fun existsByEmail(@Param("email") email: String): Boolean
}