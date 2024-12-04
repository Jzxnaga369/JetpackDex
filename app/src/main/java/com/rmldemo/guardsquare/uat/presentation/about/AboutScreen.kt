package com.rmldemo.guardsquare.uat.presentation.about

import android.os.Build
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                text = "Device Info",
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
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.data_ilustration),
            contentDescription = "Data",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Device: ${Build.BRAND} ${Build.MODEL}",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Android: ${Build.VERSION.RELEASE}",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Android Id : ${state.androidId}",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Saved UUID : ${state.savedUUID}",
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}