package ru.zar1official.chatapplication.ui.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel
import ru.zar1official.chatapplication.ui.theme.Blue


@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        viewModel.onCheckSession()

        viewModel.sharedFlowMessage.collectLatest { event ->
            when (event) {
                is BaseViewModel.BaseEvent.NavigateTo -> {
                    while (alphaAnim != 1f) {
                        delay(1L)
                    }
                    navController.popBackStack()
                    navController.navigate(event.route)
                }
                else -> {}
            }
        }
    }

    Splash(alpha = alphaAnim, scaffoldState)
}

@Composable
fun Splash(alpha: Float, scaffoldState: ScaffoldState) {
    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .background(if (isSystemInDarkTheme()) Color.Black else Blue)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}