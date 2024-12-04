package com.rmldemo.guardsquare.uat.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.sfUi

@Composable
fun FailedTransactionDialog(
    message: String,
    onClick: () -> Unit,
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.failed),
                    contentDescription = "Top Up",
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Transaction Failed",
                    fontSize = 18.sp,
                    fontFamily = sfUi,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 16.sp,
                    fontFamily = sfUi,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(36.dp))
                PrimaryButtonSmall(
                    text = "Done",
                    onClick = onClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FailedPrev() {
    FailedTransactionDialog(message = "Balance Not Enough", onClick = {})
}