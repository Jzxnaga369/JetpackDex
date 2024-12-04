package com.rmldemo.guardsquare.uat.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
    )
    {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium
        )
    }
}