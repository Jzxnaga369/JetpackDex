package com.rmldemo.guardsquare.uat.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.gson.Gson
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.presentation.component.AutoSlidingCarousel
import com.rmldemo.guardsquare.uat.ui.theme.onPrimaryLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryContainerLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import com.rmldemo.guardsquare.uat.utils.formatRupiah
import com.rmldemo.guardsquare.uat.utils.shimmerEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToTopUpScreen: () -> Unit,
    navigateToTransferScreen: () -> Unit,
    navigateToPaymentScreen: (String) -> Unit,
    navigateToQrScreen: () -> Unit,
    navigateToNotificationScreen: () -> Unit,
) {
    val state = viewModel.state

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = primaryLight),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome,",
                        fontSize = 16.sp,
                        fontFamily = sfUi,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                    )
                    if (state.isLoadingUser) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        ) {
                            Text(
                                text = "             ",
                                fontSize = 20.sp,
                                fontFamily = sfUi,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    } else {
                        Text(
                            text = state.user.name,
                            fontSize = 20.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                    }
                }
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notification",
                    tint = primaryLight,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(onPrimaryLight)
                        .padding(8.dp)
                        .height(20.dp)
                        .width(20.dp)
                        .clickable {
                            navigateToNotificationScreen()
                        }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Balance",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Thin,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            if (state.isLoadingUser) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .align(Alignment.CenterHorizontally)
                        .shimmerEffect()
                ) {
                    Text(
                        text = "               ",
                        fontSize = 24.sp,
                        fontFamily = sfUi,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            } else {
                Text(
                    text = formatRupiah(state.user.balance),
                    fontSize = 24.sp,
                    fontFamily = sfUi,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            navigateToTopUpScreen()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.top_up),
                            contentDescription = "Top Up",
                            tint = primaryLight,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Top Up",
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            navigateToTransferScreen()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.transfer),
                            contentDescription = "Transfer",
                            tint = primaryLight,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Transfer",
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            Icons.Filled.QrCode,
                            contentDescription = "Qr Code",
                            tint = primaryLight,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp)
                                .clickable {
                                    navigateToQrScreen()
                                }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Qr Code",
                            fontSize = 16.sp,
                            fontFamily = sfUi,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Payment List",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (state.isLoadingService) {
                    for (i in 0..3) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .height(28.dp)
                                    .width(28.dp)
                                    .shimmerEffect()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect()
                            ) {
                                Text(
                                    text = "          ",
                                    fontSize = 16.sp,
                                    fontFamily = sfUi,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }
                } else {
                    for (service in state.services) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.clickable {
                                navigateToPaymentScreen(Gson().toJson(service))
                            }
                        ) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .data(service.imageUrl)
                                    .build(),
                                contentDescription = service.name,
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
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = service.name,
                                fontSize = 16.sp,
                                fontFamily = sfUi,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Promo",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.promos.isNotEmpty() && !state.isLoadingPromo) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                ) {
                    AutoSlidingCarousel(
                        itemsCount = state.promos.size,
                        itemContent = { index ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .data(state.promos[index].url)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                            )
                        }
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}