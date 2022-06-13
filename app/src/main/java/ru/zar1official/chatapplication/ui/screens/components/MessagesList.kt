package ru.zar1official.chatapplication.ui.screens.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.zar1official.chatapplication.ui.models.Message

@Composable
fun MessagesList(
    messages: State<List<Message>>,
    onUploadMessages: () -> Unit,
) {
    val state = rememberLazyListState()
    val key by rememberUpdatedState(newValue = state.firstVisibleItemIndex)
    LaunchedEffect(key1 = key, block = {
        if (state.firstVisibleItemIndex + state.layoutInfo.visibleItemsInfo.size == messages.value.size) {
            onUploadMessages()
        }
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        reverseLayout = true,
        state = state
    ) {
        items(messages.value) {
            Message(
                message = it
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}