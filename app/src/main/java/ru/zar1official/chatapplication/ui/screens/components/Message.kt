package ru.zar1official.chatapplication.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.zar1official.chatapplication.ui.models.Message
import ru.zar1official.chatapplication.ui.theme.LighterGray
import ru.zar1official.chatapplication.util.DateTimeUtil

@Composable
fun Message(message: Message) {
    val ownMessageColor = MaterialTheme.colors.secondary
    val otherMessageColor = LighterGray
    val textColor = Color.White
    Column {
        Box(
            contentAlignment = if (message.isOwnMessage)
                Alignment.CenterEnd
            else Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = if (message.isOwnMessage) ownMessageColor else otherMessageColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = message.username,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = message.text,
                    color = textColor
                )
                Text(
                    text = DateTimeUtil.millisToDateTime(message.timestamp),
                    color = textColor,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}