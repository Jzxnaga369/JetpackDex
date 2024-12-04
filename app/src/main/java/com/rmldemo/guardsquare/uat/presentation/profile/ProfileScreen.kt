package com.rmldemo.guardsquare.uat.presentation.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rmldemo.guardsquare.uat.presentation.component.LoadingDialog
import com.rmldemo.guardsquare.uat.presentation.component.SelectImageDialog
import com.rmldemo.guardsquare.uat.ui.theme.primaryContainerLight
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToMapScreen: () -> Unit,
    navigateToAudioScreen: () -> Unit,
    navigateToVideoScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    navigateToAboutScreen: () -> Unit,
    navigateToAppAttestationScreen: () -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onEvent(ProfileEvent.OnChangePhoto(it))
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            bitmapToUri(context, it)?.let { uri ->
                viewModel.onEvent(ProfileEvent.OnChangePhoto(uri))
            }
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    if (state.pickImage) {
        SelectImageDialog(
            openCamera = {
                when {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                        cameraLauncher.launch()
                    }
                    else -> {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
            openGallery = {
                galleryLauncher.launch("image/*")
            }
        )
    }

    if (state.isLoadingUploadPhoto) {
        LoadingDialog()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontSize = 18.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .height(100.dp)
                    .width(100.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.user.photoUrl)
                    .build(),
                contentDescription = state.user.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(100.dp)
                    .width(100.dp)
            )
            Icon(
                Icons.Filled.AddAPhoto,
                contentDescription = "Add Photo",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(primaryLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
                    .clickable {
                        viewModel.onEvent(ProfileEvent.OnPickImage(true))
                    }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.user.name,
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = state.user.email,
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.version,
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToMapScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Map",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Map",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = primaryContainerLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToAudioScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.AudioFile,
                contentDescription = "Audio",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Audio Player",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = primaryContainerLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToVideoScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.VideoFile,
                contentDescription = "Video",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Video Player",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = primaryContainerLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToAboutScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Info,
                contentDescription = "About",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Device Info",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = primaryContainerLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToAppAttestationScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Security,
                contentDescription = "App Attestation",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "App Attestation",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = primaryContainerLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onEvent(ProfileEvent.OnLogout)
                    navigateToLoginScreen()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Logout,
                contentDescription = "Logout",
                tint = primaryLight,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primaryContainerLight)
                    .padding(8.dp)
                    .height(20.dp)
                    .width(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(10f)
            )
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = "Next",
                tint = primaryLight,
            )
        }
    }
}

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image_${System.currentTimeMillis()}.jpg")
    return try {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        FileProvider.getUriForFile(context, "com.rmldemo.guardsquare.uat.fileprovider", file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}