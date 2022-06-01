package ru.zar1official.chatapplication.ui.screens.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.zar1official.chatapplication.ui.models.Message

@Composable
fun MessagesList(messages: State<List<Message>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        reverseLayout = true
    ) {
        items(messages.value) {
            Message(
                message = it
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}