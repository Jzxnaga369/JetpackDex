package com.rmldemo.guardsquare.uat.presentation.qr

import android.graphics.Bitmap
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmldemo.guardsquare.uat.presentation.component.CustomOutlineTextField
import com.rmldemo.guardsquare.uat.presentation.component.PrimaryButton
import com.rmldemo.guardsquare.uat.presentation.component.ResultQrDialog
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight
import com.rmldemo.guardsquare.uat.utils.generateQRCode

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QrScreen(
    navigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var dataText by remember { mutableStateOf(TextFieldValue("")) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    bitmap?.let {
        ResultQrDialog(
            bitmap = it,
            onClick = {
                bitmap = null
            }
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
                text = "Generate Qr Code",
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
            painter = painterResource(id = R.drawable.qr_illustration),
            contentDescription = "Top Up",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlineTextField(
            hint = "Enter Your Data",
            textState = dataText,
            onTextChange = {
               dataText = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = "Generate",
            onClick = {
                bitmap = generateQRCode(dataText.text, 500)
                keyboardController?.hide()
            }
        )
    }
}