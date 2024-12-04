package com.rmldemo.guardsquare.uat.presentation.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.ui.theme.surfaceLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
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
                text = "Notification",
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
        if (state.notifications.isEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.empty_illustration),
                contentDescription = "Empty",
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Notification",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            for (notification in state.notifications) {
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = primaryLight,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(12.dp),
                    ) {
                        Text(
                            text = notification.title,
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(
                                Date(
                                    notification.sentTime
                                )
                            ),
                            fontSize = 14.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Thin
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = notification.message,
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}