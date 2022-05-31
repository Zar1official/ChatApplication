package ru.zar1official.chatapplication.ui.screens.private_chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import ru.zar1official.chatapplication.ui.screens.components.BaseEventEffect
import ru.zar1official.chatapplication.ui.screens.components.Message
import ru.zar1official.chatapplication.ui.screens.components.MessageFieldSection
import ru.zar1official.chatapplication.ui.screens.dialogs.DialogViewModel

@Composable
fun DialogScreen(
    navController: NavController,
    viewModel: DialogViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    username: String,
    dialogId: Int
) {
    val resources = LocalContext.current.resources
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val messageText = viewModel.messageText.observeAsState(initial = "")
    val messages = viewModel.messages.observeAsState(initial = listOf())

    BaseEventEffect(
        viewModel = viewModel,
        navController = navController,
        scaffoldState = scaffoldState,
        resources = resources
    )

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToDialog(dialogId)
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnectDialog()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.onGoBack(navController = navController)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = username, style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                        .background(color = Color.White)
                ) {
                    Column {
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

                        MessageFieldSection(
                            messageText = messageText,
                            onChangeMessage = { viewModel.onChangeMessage(it) },
                            onSendMessage = { viewModel.onSendMessage() })
                    }
                }
            }
        }
    }

}