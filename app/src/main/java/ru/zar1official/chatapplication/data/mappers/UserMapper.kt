package ru.zar1official.chatapplication.data.mappers

import models.UserEntity
import ru.zar1official.chatapplication.ui.models.User
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun mapFromEntity(entity: UserEntity, session: UserEntity): User {
        return User(
            userId = entity.userId,
            username = entity.username,
            password = entity.password,
            isMe = entity.userId == session.userId
        )
    }

    fun mapToEntity(model: User): UserEntity {
        return UserEntity(
            userId = model.userId,
            username = model.username,
            password = model.password
        )
    }

}