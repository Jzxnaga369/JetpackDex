package com.rmldemo.guardsquare.uat.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmldemo.guardsquare.uat.presentation.component.CustomOutlineTextField
import com.rmldemo.guardsquare.uat.presentation.component.LoadingDialog
import com.rmldemo.guardsquare.uat.presentation.component.PrimaryButton
import com.rmldemo.guardsquare.uat.ui.theme.sfUi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current

    if (!state.isLoading && !state.isSuccess && state.message != "") {
        LaunchedEffect(key1 = true) {
            snackbarHostState.showSnackbar(
                message = state.message
            )
        }
    }

    if (!state.isLoading && state.isSuccess) {
        navigateToHomeScreen()
    }

    if (state.isLoading) {
        LoadingDialog()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Sign In",
            fontSize = 24.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Email",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium
        )
        CustomOutlineTextField(
            hint = "Enter Your Email",
            textState = state.email,
            onTextChange = {
                viewModel.onEvent(LoginEvent.OnEmailChange(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Password",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium
        )
        CustomOutlineTextField(
            hint = "Enter Your Password",
            textState = state.password,
            onTextChange = {
                viewModel.onEvent(LoginEvent.OnPasswordChange(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = "Sign In",
            onClick = {
                viewModel.onEvent(LoginEvent.OnLogin)
                keyboardController?.hide()
            }
        )
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account ?",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.clickable {
                  navigateToRegisterScreen()
                },
                text = " Sign Up",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}