package ru.zar1official.chatapplication.ui.screens.general_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch
import ru.zar1official.chatapplication.ui.screens.components.*

@Composable
fun GeneralChatScreen(
    navController: NavController,
    viewModel: GeneralChatViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val messageText = viewModel.messageText.observeAsState(initial = "")
    val messages = viewModel.messages.observeAsState(initial = listOf())
    val users = viewModel.filteredUsers.observeAsState(initial = listOf())
    val userFilter = viewModel.userFilter.observeAsState(initial = "")
    val resources = LocalContext.current.resources
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val isLoading = viewModel.isLoading.observeAsState(initial = true)

    BaseEventEffect(
        viewModel = viewModel,
        navController = navController,
        scaffoldState = scaffoldState,
        resources = resources
    )

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToGeneralChat()
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                viewModel.disconnectGeneralChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                scope.launch { scaffoldState.drawerState.close() }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RoundedCornerShape(topEnd = 30.dp),
        drawerElevation = 5.dp,
        drawerGesturesEnabled = true,
        drawerContent = {
            Column {
                SearchView(
                    user = userFilter,
                    onValueChange = { viewModel.onSearchUser(it) })
                LazyColumn(content = {
                    items(items = users.value) { user ->
                        Spacer(modifier = Modifier.height(5.dp))
                        Card(
                            shape = RoundedCornerShape(30.dp),
                            elevation = 0.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !user.isMe) {
                                    viewModel.onNavigateDialogScreen(user)
                                }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Email, "")
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = if (!user.isMe) user.username else user.username + " (вы)",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Light,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                }, contentPadding = PaddingValues(10.dp))
            }
        },
    ) {
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
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = { viewModel.onNavigateDialogListScreen() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = { viewModel.onLogout() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
                RoundedBox {
                    if (!isLoading.value) {
                        Column {
                            MessagesList(messages = messages)
                            MessageFieldSection(
                                messageText = messageText,
                                onChangeMessage = { viewModel.onChangeMessage(it) },
                                onSendMessage = { viewModel.onSendMessage() })
                        }
                    } else {
                        LoadingSection()
                    }
                }
            }
        }
    }
}