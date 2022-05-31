package ru.zar1official.chatapplication.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BorderedTextField(
    icon: ImageVector,
    text: State<String>,
    onChangeText: (value: String) -> Unit,
    @StringRes placeHolder: Int
) {
    OutlinedTextField(
        modifier = Modifier
            .requiredWidth(330.dp),
        value = text.value,
        onValueChange = { onChangeText(it) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = MaterialTheme.colors.secondary
            )
        },
        shape = RoundedCornerShape(30.dp),
        placeholder = {
            Text(
                text = stringResource(id = placeHolder),
                color = MaterialTheme.colors.secondary
            )
        },
        singleLine = true
    )
}
