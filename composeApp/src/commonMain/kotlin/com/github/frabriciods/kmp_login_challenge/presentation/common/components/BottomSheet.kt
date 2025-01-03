package com.github.frabriciods.kmp_login_challenge.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_login_challenge.composeapp.generated.resources.Res
import kmp_login_challenge.composeapp.generated.resources.bottomSheet_button_text
import kmp_login_challenge.composeapp.generated.resources.bottomSheet_error_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun InformativeBottomSheet(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                Res.string.bottomSheet_error_title),
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = onClose) {
            Text(text = stringResource(Res.string.bottomSheet_button_text))
        }
    }
}