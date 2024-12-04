package com.rmldemo.guardsquare.uat.presentation.topup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmldemo.guardsquare.uat.presentation.component.CustomOutlineTextField
import com.rmldemo.guardsquare.uat.presentation.component.FailedTransactionDialog
import com.rmldemo.guardsquare.uat.presentation.component.LoadingDialog
import com.rmldemo.guardsquare.uat.presentation.component.PrimaryButton
import com.rmldemo.guardsquare.uat.presentation.component.SuccessTransactionDialog
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopUpScreen(
    viewModel: TopUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current

    if (state.isLoading) {
        LoadingDialog()
    }

    if (!state.isLoading && state.isSuccess) {
        SuccessTransactionDialog(
            name = "Top Up",
            onClick = navigateBack
        )
    }
    if (!state.isLoading && !state.isSuccess && state.message != "") {
        FailedTransactionDialog(
            message = state.message,
            onClick = navigateBack,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back",
                tint = primaryLight,
                modifier = Modifier.clickable {
                    navigateBack()
                }
            )
            Text(
                text = "Top Up",
                fontSize = 18.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                tint = surfaceLight,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.top_up_illustration),
            contentDescription = "Top Up",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlineTextField(
            hint = "Enter Amount",
            textState = state.amount,
            onTextChange = {
               viewModel.onEvent(TopUpEvent.OnAmountChange(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = "Top Up",
            onClick = {
                viewModel.onEvent(TopUpEvent.OnTopUp)
                keyboardController?.hide()
            }
        )
    }
}