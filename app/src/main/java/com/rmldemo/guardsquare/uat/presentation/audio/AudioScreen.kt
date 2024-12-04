package com.rmldemo.guardsquare.uat.presentation.audio

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryContainerLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight

@Composable
fun AudioScreen(
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    var mediaPlayer = MediaPlayer.create(context, R.raw.sample_audio)

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
                text = "Audio Player",
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
            painter = painterResource(id = R.drawable.audio_illustration),
            contentDescription = "Top Up",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = "Audio Player",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .clickable {
                        mediaPlayer.start()
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                Icons.Filled.Stop,
                contentDescription = "Audio Player",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .clickable {
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(context, R.raw.sample_audio)
                    }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}