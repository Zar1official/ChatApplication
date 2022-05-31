package ru.zar1official.chatapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(val userId: Int = 0, val username: String, val password: String) {
    fun isEmpty(): Boolean {
        return userId == 0
    }
}