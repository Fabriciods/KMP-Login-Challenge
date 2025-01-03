package com.github.frabriciods.kmp_login_challenge.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.presentation.common.components.InformativeBottomSheet
import com.github.frabriciods.kmp_login_challenge.presentation.common.components.Input
import com.github.frabriciods.kmp_login_challenge.presentation.common.components.InputType
import com.github.frabriciods.kmp_login_challenge.util.BaseError
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.UserPreferences
import kmp_login_challenge.composeapp.generated.resources.Res
import kmp_login_challenge.composeapp.generated.resources.login_button_text
import kmp_login_challenge.composeapp.generated.resources.login_input_label
import kmp_login_challenge.composeapp.generated.resources.login_screen_title
import kmp_login_challenge.composeapp.generated.resources.password_input_label
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navigateToHome: () -> Unit) {

    val viewModel = koinViewModel<LoginViewModel>()
    var userName by remember { mutableStateOf(TextFieldValue(String())) }
    var password by remember { mutableStateOf(TextFieldValue(String())) }
    var buttonLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val userPreferences = koinInject<UserPreferences>()
    MaterialTheme {
        ModalBottomSheetLayout(
            sheetContent = {
                InformativeBottomSheet(onClose = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                })
            },
            sheetState = bottomSheetState
        ) {
            Box(
                Modifier.fillMaxSize()
            ) {
                Column(
                    Modifier.fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(Res.string.login_screen_title),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Input(
                            value = userName.text,
                            onValueChange = {
                                userName = TextFieldValue(it)
                                isError = false
                            },
                            label = stringResource(Res.string.login_input_label),
                            type = InputType.TEXT,
                            isError = isError,

                            )
                        Input(
                            value = password.text,
                            onValueChange = {
                                password = TextFieldValue(it)
                                isError = false
                            },
                            label = stringResource(Res.string.password_input_label),
                            type = InputType.PASSWORD,
                            isError = isError
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.makeLogin(LoginModel(
                                login = userName.text,
                                password = password.text
                            ),
                                handleSuccess = {
                                    navigateToHome.invoke()
                                    userPreferences.saveUserName(it.userName)
                                },
                                handleError = { error ->
                                    handleError(
                                        error = error,
                                        inputErrorState = { isError = it },
                                        isBottomSheetOpen = {
                                            scope.launch {
                                                bottomSheetState.show()
                                            } }
                                    )
                                },
                                toggleLoading = {
                                    buttonLoading = !buttonLoading
                                }
                            )
                        },
                        enabled = userName.text.isNotBlank() && password.text.isNotBlank()
                    ) {
                        if (buttonLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(Res.string.login_button_text),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp
                            )
                        }

                    }
                }
            }
        }

    }
}

private fun handleError(
    error: BaseError,
    inputErrorState: (Boolean) -> Unit,
    isBottomSheetOpen: () -> Unit,
) {
    if (error == NetworkBaseError.UNAUTHORIZED_ERROR) {
        inputErrorState.invoke(true)
    } else {
        isBottomSheetOpen.invoke()
    }

}