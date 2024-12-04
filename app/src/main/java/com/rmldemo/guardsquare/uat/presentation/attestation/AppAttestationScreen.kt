package com.rmldemo.guardsquare.uat.presentation.attestation

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.presentation.component.LoadingDialog
import com.rmldemo.guardsquare.uat.presentation.component.PrimaryButton
import com.rmldemo.guardsquare.uat.ui.theme.primaryContainerLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight

@Composable
fun AppAttestationScreen(
    viewModel: AppAttestationViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val state = viewModel.state

    if (!state.isLoading && state.token.dateExpiredJwt.isEmpty() && state.message != "") {
        LaunchedEffect(key1 = true) {
            snackbarHostState.showSnackbar(
                message = state.message
            )
        }
    }

    if (state.isLoading) {
        LoadingDialog()
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
                text = "App Attestation",
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
            painter = painterResource(id = R.drawable.app_attestation_illustration),
            contentDescription = "App Attestation",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            text = "Check",
            onClick = {
                viewModel.onEvent(AppAttestationEvent.CheckToken)
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Protocol Error",
                fontSize = 18.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = state.token.protocolErrorName,
                fontSize = 16.sp,
                fontFamily = sfUi,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Effect",
                fontSize = 18.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = state.token.effectName,
                fontSize = 16.sp,
                fontFamily = sfUi,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Expired Date",
                fontSize = 18.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = state.token.dateExpiredJwt,
                fontSize = 16.sp,
                fontFamily = sfUi,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}