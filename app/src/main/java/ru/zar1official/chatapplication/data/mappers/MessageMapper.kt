package ru.zar1official.chatapplication.data.mappers

import ru.zar1official.chatapplication.data.models.MessageEntity
import ru.zar1official.chatapplication.data.models.UserEntity
import ru.zar1official.chatapplication.ui.models.Message
import javax.inject.Inject


class MessageMapper @Inject constructor() {
    fun mapFromEntity(entity: MessageEntity, session: UserEntity): Message {
        return Message(
            username = entity.senderUserName,
            text = entity.text,
            isOwnMessage = session.username == entity.senderUserName,
            timestamp = entity.timestamp,
            messageId = entity.messageId
        )
    }

    fun mapToEntity(model: Message): MessageEntity {
        return MessageEntity(
            senderUserName = model.username,
            text = model.text,
            timestamp = model.timestamp
        )
    }
}