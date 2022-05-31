package ru.zar1official.chatapplication.ui.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.zar1official.chatapplication.R

@Composable
fun MessageFieldSection(
    messageText: State<String>,
    onChangeMessage: (value: String) -> Unit,
    onSendMessage: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText.value,
            onValueChange = { onChangeMessage(it) },
            placeholder = {
                Text(text = stringResource(id = R.string.message_text_hint))
            },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            onSendMessage()
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}