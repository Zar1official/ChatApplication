package ru.zar1official.chatapplication.ui.screens

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.zar1official.chatapplication.navigation.Screens
import ru.zar1official.chatapplication.ui.screens.general_chat.GeneralChatScreen
import ru.zar1official.chatapplication.ui.screens.login.LoginScreen
import ru.zar1official.chatapplication.ui.screens.private_chats.DialogScreen
import ru.zar1official.chatapplication.ui.screens.register.RegisterScreen
import ru.zar1official.chatapplication.ui.screens.splash.SplashScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController, scaffoldState = scaffoldState)
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(Screens.GeneralChatScreen.route) {
            GeneralChatScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            Screens.DialogScreen.route,
            arguments = listOf(
                navArgument(Screens.usernameArg) { type = NavType.StringType },
                navArgument(Screens.dialogIdArg) { type = NavType.IntType })
        ) {
            val username = it.arguments?.getString(Screens.usernameArg) ?: ""
            val dialogId = it.arguments?.getInt(Screens.dialogIdArg) ?: 0

            DialogScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                username = username,
                dialogId = dialogId
            )
        }
    }
}
