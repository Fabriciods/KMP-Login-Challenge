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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.github.frabriciods.kmp_login_challenge.presentation.common.ScreenState
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
    val screenState by viewModel.loginState.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        when(screenState){
            is ScreenState.Success -> {navigateToHome()}
            is ScreenState.Error -> {
                scope.launch { bottomSheetState.show() }
            }
            else -> {}
        }
    }
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
                            value = viewModel.userName,
                            onValueChange = viewModel::onUserNameChange,
                            label = stringResource(Res.string.login_input_label),
                            type = InputType.TEXT,
                            isError = viewModel.isInputStateError,

                            )
                        Input(
                            value = viewModel.userName,
                            onValueChange = viewModel::onPasswordChange,
                            label = stringResource(Res.string.password_input_label),
                            type = InputType.PASSWORD,
                            isError = viewModel.isInputStateError
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.makeLogin()
                        },
                        enabled = viewModel.userName.isNotBlank() && viewModel.password.isNotBlank()
                    ) {
                        if (viewModel.isButtonLoading) {
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
