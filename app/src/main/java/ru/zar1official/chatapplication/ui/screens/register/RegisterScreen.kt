package ru.zar1official.chatapplication.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.zar1official.chatapplication.R
import ru.zar1official.chatapplication.ui.screens.components.BaseEventEffect
import ru.zar1official.chatapplication.ui.screens.components.BorderedTextField
import ru.zar1official.chatapplication.ui.screens.login.LoginViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val login = viewModel.login.observeAsState(initial = "")
    val password = viewModel.password.observeAsState(initial = "")
    val repeatPassword = viewModel.repeatPassword.observeAsState(initial = "")
    val resources = LocalContext.current.resources

    BaseEventEffect(
        viewModel = viewModel,
        navController = navController,
        scaffoldState = scaffoldState,
        resources = resources
    )

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = resources.getString(R.string.register_title),
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = resources.getString(R.string.register_subtitle),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.secondary
                        )
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    BorderedTextField(
                        icon = Icons.Filled.Email,
                        text = login,
                        onChangeText = { viewModel.onChangeLogin(it) },
                        placeHolder = R.string.login_placeholder
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    BorderedTextField(
                        icon = Icons.Filled.Lock,
                        text = password,
                        onChangeText = { viewModel.onChangePassword(it) },
                        placeHolder = R.string.password_placeholder
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    BorderedTextField(
                        icon = Icons.Filled.Lock,
                        text = repeatPassword,
                        onChangeText = { viewModel.onChangeRepeatPassword(it) },
                        placeHolder = R.string.repeat_password_placeholder
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(modifier = Modifier.size(width = 185.dp, height = 45.dp), onClick = {
                        viewModel.onRegister()
                    }) {
                        Text(
                            text = resources.getString(R.string.register_button),
                            style = TextStyle(
                                fontSize = 22.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row {
                        Text(
                            text = resources.getString(R.string.has_account_title),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colors.secondary
                            )
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = resources.getString(R.string.nav_login_button),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colors.primary
                            ),
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}