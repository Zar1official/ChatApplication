package ru.zar1official.chatapplication.navigation

sealed class Screens(private val startRoute: String, private val args: String = "") {
    object SplashScreen : Screens("splash")
    object LoginScreen : Screens("login")
    object RegisterScreen : Screens("register")
    object GeneralChatScreen : Screens("general_chat")
    object DialogScreen : Screens("dialog_screen", "/{$dialogIdArg}/{$usernameArg}")

    val route
        get() = startRoute + args

    companion object {
        const val dialogIdArg = "dialogId"
        const val usernameArg = "username"

        fun Screens.buildPath(vararg arguments: Any): String {
            return buildString {
                append(startRoute)
                arguments.forEach {
                    append("/$it")
                }
            }
        }
    }
}
