package ru.zar1official.chatapplication.data.mappers

import ru.zar1official.chatapplication.data.models.DialogMessageEntity
import ru.zar1official.chatapplication.data.models.MessageEntity
import ru.zar1official.chatapplication.data.models.UserEntity
import ru.zar1official.chatapplication.ui.models.DialogMessage
import ru.zar1official.chatapplication.ui.models.Message
import javax.inject.Inject

class DialogMessageMapper @Inject constructor() {
    fun mapFromEntity(entity: DialogMessageEntity, session: UserEntity): DialogMessage {
        return DialogMessage(
            messageId = entity.messageId,
            dialogId = entity.dialogId,
            username = entity.sender,
            timestamp = entity.timestamp,
            text = entity.text,
            isOwnMessage = session.username == entity.sender
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