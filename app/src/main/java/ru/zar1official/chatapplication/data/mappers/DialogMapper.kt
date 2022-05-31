package ru.zar1official.chatapplication.data.mappers

import ru.zar1official.chatapplication.data.models.DialogEntity
import ru.zar1official.chatapplication.ui.models.Dialog
import javax.inject.Inject

class DialogMapper @Inject constructor() {
    fun mapFromEntity(entity: DialogEntity): Dialog {
        return Dialog(
            dialogId = entity.dialogId,
            creatorId = entity.creatorId,
            companionId = entity.companionId
        )
    }

    fun mapToEntity(model: Dialog): DialogEntity {
        return DialogEntity(
            dialogId = model.dialogId,
            creatorId = model.creatorId,
            companionId = model.companionId
        )
    }
}