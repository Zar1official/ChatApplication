package ru.zar1official.chatapplication.data.mappers

import models.DialogMessageEntity
import models.UserEntity
import ru.zar1official.chatapplication.ui.models.DialogMessage
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
}