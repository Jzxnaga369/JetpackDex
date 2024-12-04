package com.rmldemo.guardsquare.uat.presentation.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.rmldemo.guardsquare.uat.domain.model.TransactionType
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.ui.theme.primaryContainerLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.utils.formatRupiah
import com.rmldemo.guardsquare.uat.utils.shimmerEffect

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "History",
            fontSize = 18.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (state.isLoading) {
            for (i in 0..3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(28.dp)
                            .width(28.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        ) {
                            Text(
                                text = "",
                                fontSize = 16.sp,
                                fontFamily = sfUi,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        ) {
                            Text(
                                text = "",
                                fontSize = 14.sp,
                                fontFamily = sfUi,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(10f))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    ) {
                        Text(
                            text = "",
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        } else if (state.histories.isEmpty()) {
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
                text = "No History",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            for (history in state.histories) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .decoderFactory(SvgDecoder.Factory())
                            .data(history.imageUrl)
                            .build(),
                        contentDescription = history.name,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(primaryContainerLight)
                            .padding(8.dp)
                            .height(20.dp)
                            .width(20.dp)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = contentDescription,
                            colorFilter = ColorFilter.tint(primaryLight)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = history.name,
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = history.createdAt,
                            fontSize = 14.sp,
                            fontFamily = sfUi,
                        )
                    }
                    Text(
                        text = if (history.type == TransactionType.PLUS) "+" + " " + formatRupiah(
                            history.amount.toLong()
                        ) else "-" + " " + formatRupiah(history.amount),
                        fontSize = 16.sp,
                        fontFamily = sfUi,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.End,
                        color = if (history.type == TransactionType.PLUS) Color.Green else Color.Red,
                        modifier = Modifier.weight(10f)
                    )
                }
            }
        }
    }
}