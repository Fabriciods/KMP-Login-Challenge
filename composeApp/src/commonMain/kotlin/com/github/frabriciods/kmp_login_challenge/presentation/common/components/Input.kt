package com.github.frabriciods.kmp_login_challenge.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kmp_login_challenge.composeapp.generated.resources.Res
import kmp_login_challenge.composeapp.generated.resources.baseline_visibility_24
import kmp_login_challenge.composeapp.generated.resources.baseline_visibility_off_24
import kmp_login_challenge.composeapp.generated.resources.input_login_error
import kmp_login_challenge.composeapp.generated.resources.visibility_off_icon_description
import kmp_login_challenge.composeapp.generated.resources.visibility_on_icon_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Input(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    type: InputType,
    isError: Boolean
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = isError,
            visualTransformation = if (type == InputType.PASSWORD && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (type == InputType.PASSWORD) {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) Res.drawable.baseline_visibility_24
                                else Res.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = if (passwordVisible) stringResource(Res.string.visibility_off_icon_description
                            ) else stringResource(Res.string.visibility_on_icon_description)
                        )
                    }
                }
            }
        )
        if (isError) {
            Text(
                text = stringResource(Res.string.input_login_error),
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                color = MaterialTheme.colors.error
            )
        }
    }
}

enum class InputType {
    TEXT,
    PASSWORD
}