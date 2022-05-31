package ru.zar1official.chatapplication.ui.screens.components

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel

@Composable
fun BaseEventEffect(
    viewModel: BaseViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState,
    resources: Resources
) {
    LaunchedEffect(key1 = true, block = {
        viewModel.sharedFlowMessage.collectLatest { event ->
            when (event) {
                is BaseViewModel.BaseEvent.ShowMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = resources.getString(event.message),
                        duration = SnackbarDuration.Short,
                    )
                }
                is BaseViewModel.BaseEvent.NavigateTo -> {
                    if (event.clearBackStack) {
                        navController.popBackStack()
                    }
                    navController.navigate(event.route)
                }

                is BaseViewModel.BaseEvent.ShowTextMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    })
}