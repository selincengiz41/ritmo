package com.selincengiz.ritmo.presentation.login


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.common.GlowingCard
import com.selincengiz.ritmo.ui.theme.BlueButtonColor
import com.selincengiz.ritmo.ui.theme.PurpleButtonColor

@Composable
fun LoginScreen(loginState: LoginState, event: (LoginEvent) -> Unit, navigateToHome: () -> Unit) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Error -> {
                snackbarHostState.showSnackbar(loginState.throwable)
            }

            is LoginState.Success -> {
                snackbarHostState.showSnackbar("Success", duration = SnackbarDuration.Short)
                navigateToHome()
            }

            else -> {

            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.login_title),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.display_small)
            )

            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = {
                    Text(
                        stringResource(id = R.string.email),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }, modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.white),
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        stringResource(id = R.string.password),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)

                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.white),
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            GlowingCard(
                onClick = {
                    event(LoginEvent.Login(userName, password))
                },
                glowingColor = BlueButtonColor,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(42.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(BlueButtonColor, PurpleButtonColor)
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ),
                cornersRadius = Int.MAX_VALUE.dp
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.login),
                    color = colorResource(id = R.color.white)
                )
            }
        }
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            hostState = snackbarHostState
        )
    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    LoginScreen(loginState = LoginState.Entry, event = {}, navigateToHome = {})
}