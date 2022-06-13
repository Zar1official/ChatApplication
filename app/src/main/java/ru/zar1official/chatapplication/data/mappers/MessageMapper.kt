package ru.zar1official.chatapplication.data.mappers

import models.GeneralChatMessageEntity
import models.UserEntity
import ru.zar1official.chatapplication.ui.models.Message
import javax.inject.Inject


class MessageMapper @Inject constructor() {
    fun mapFromEntity(entity: GeneralChatMessageEntity, session: UserEntity): Message {
        return Message(
            username = entity.senderUserName,
            text = entity.text,
            isOwnMessage = session.username == entity.senderUserName,
            timestamp = entity.timestamp,
            messageId = entity.messageId
        )
    }

    fun mapToEntity(model: Message): GeneralChatMessageEntity {
        return GeneralChatMessageEntity(
            senderUserName = model.username,
            text = model.text,
            timestamp = model.timestamp
        )
    }
}