package com.rmldemo.guardsquare.uat.presentation.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.huawei.hms.api.ConnectionResult
import com.rmldemo.guardsquare.uat.BuildConfig
import com.rmldemo.guardsquare.uat.R
import com.rmldemo.guardsquare.uat.domain.model.DeviceLog
import com.rmldemo.guardsquare.uat.presentation.component.DeviceBlockDialog
import com.rmldemo.guardsquare.uat.ui.theme.primaryLight
import com.rmldemo.guardsquare.uat.ui.theme.sfUi
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("HardwareIds")
@Composable
fun SplashScreen(
    sharedPreferences: SharedPreferences,
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val isGpsEnabled = locationManager
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val fusedLocationClientHMS = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context)

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult){
            super.onLocationResult(p0)
            for (location in p0.locations) {
                viewModel.onEvent(
                    SplashEvent.PostDeviceLog(
                        DeviceLog(
                            deviceName = Build.DEVICE,
                            systemName = "Android",
                            systemVersion = Build.VERSION.RELEASE,
                            model = Build.BRAND,
                            deviceModel = Build.MODEL,
                            locationModel = Build.MODEL,
                            uuid = Build.ID,
                            deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                            createdAt = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault()
                            ).format(
                                Date()
                            ),
                            currentAddress =
                                getLocationName(
                                context,
                                location.latitude,
                                location.longitude
                            ),
                            token = "Token"
                        )
                    )
                )

                fusedLocationClient.removeLocationUpdates(this)
            }
        }
    }
    val locationCallbackHMS = object : com.huawei.hms.location.LocationCallback() {
        override fun onLocationResult(p0: com.huawei.hms.location.LocationResult){
            super.onLocationResult(p0)
            for (location in p0.locations) {
                viewModel.onEvent(
                    SplashEvent.PostDeviceLog(
                        DeviceLog(
                            deviceName = Build.DEVICE,
                            systemName = "Android",
                            systemVersion = Build.VERSION.RELEASE,
                            model = Build.BRAND,
                            deviceModel = Build.MODEL,
                            locationModel = Build.MODEL,
                            uuid = Build.ID,
                            deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                            createdAt = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault()
                            ).format(
                                Date()
                            ),
                            currentAddress =
                                getLocationName(
                                context,
                                location.latitude,
                                location.longitude
                            ),
                            token = "Token"
                        )
                    )
                )

                fusedLocationClientHMS.removeLocationUpdates(this)
            }
        }
    }

    val permissions=arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    val launchMultiplePermissions = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionLocation->
        val areGranted=permissionLocation.values.reduce{acc,next->acc && next}
        if(areGranted) {
            if (isGpsEnabled) {
                if (isGooglePlayServicesAvailable(context)) {
                    startLocationUpdates(fusedLocationClient, locationCallback)
                } else {
                    startLocationUpdatesHMS(fusedLocationClientHMS, locationCallbackHMS)
                }
            } else {
                viewModel.onEvent(
                    SplashEvent.PostDeviceLog(
                        DeviceLog(
                            deviceName = Build.DEVICE,
                            systemName = "Android",
                            systemVersion = Build.VERSION.RELEASE,
                            model = Build.BRAND,
                            deviceModel = Build.MODEL,
                            locationModel = Build.MODEL,
                            uuid = Build.ID,
                            deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                            createdAt = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault()
                            ).format(
                                Date()
                            ),
                            currentAddress = "GPS Disable",
                            token = "Token"
                        )
                    )
                )

                fusedLocationClient.removeLocationUpdates(locationCallback)
                fusedLocationClientHMS.removeLocationUpdates(locationCallbackHMS)
            }
        } else {
            viewModel.onEvent(
                SplashEvent.PostDeviceLog(
                    DeviceLog(
                        deviceName = Build.DEVICE,
                        systemName = "Android",
                        systemVersion = Build.VERSION.RELEASE,
                        model = Build.BRAND,
                        deviceModel = Build.MODEL,
                        locationModel = Build.MODEL,
                        uuid = Build.ID,
                        deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                        createdAt = SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()
                        ).format(
                            Date()
                        ),
                        currentAddress = "Location Permission Denied",
                        token = "Token"
                    )
                )
            )

            fusedLocationClient.removeLocationUpdates(locationCallback)
            fusedLocationClientHMS.removeLocationUpdates(locationCallbackHMS)
        }
    }

    LaunchedEffect(Unit){
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            if (isGpsEnabled) {
                if (isGooglePlayServicesAvailable(context)) {
                    startLocationUpdates(fusedLocationClient, locationCallback)
                } else {
                    startLocationUpdatesHMS(fusedLocationClientHMS, locationCallbackHMS)
                }
            } else {
                viewModel.onEvent(
                    SplashEvent.PostDeviceLog(
                        DeviceLog(
                            deviceName = Build.DEVICE,
                            systemName = "Android",
                            systemVersion = Build.VERSION.RELEASE,
                            model = Build.BRAND,
                            deviceModel = Build.MODEL,
                            locationModel = Build.MODEL,
                            uuid = Build.ID,
                            deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                            createdAt = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault()
                            ).format(
                                Date()
                            ),
                            currentAddress = "GPS Disable",
                            token = "Token"
                        )
                    )
                )

                fusedLocationClient.removeLocationUpdates(locationCallback)
                fusedLocationClientHMS.removeLocationUpdates(locationCallbackHMS)
            }
        } else {
            launchMultiplePermissions.launch(permissions)
        }
    }

    if (state.deviceLog != "Loading Post Device Log") {
        if (!state.deviceLog.contains("500")) {
            if (state.user.email != "") {
                LaunchedEffect(Unit) {
                    delay(500)
                    navigateToHomeScreen()
                }
            }
            if (state.user.email == "") {
                LaunchedEffect(Unit) {
                    delay(500)
                    navigateToLoginScreen()
                }
            }
        } else {
            DeviceBlockDialog(state.deviceLog)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = "Home Icon",
                modifier = Modifier.size(100.dp)// You can set the color as needed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "JetpackDex",
                fontSize = 24.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(2f))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = BuildConfig.VERSION_NAME,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Android Id : ${state.getAndroidId}",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "UUID : ${state.getDeviceId}",
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.firebase,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.version,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (state.deviceLog == "500") "Device Blocked" else state.deviceLog ,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.encryptionData,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.startZoloz,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@SuppressLint("MissingPermission")
private fun startLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    locationCallback: LocationCallback
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000)
        .setWaitForAccurateLocation(false)
        .setMinUpdateIntervalMillis(3000)
        .setMaxUpdateDelayMillis(1000)
        .build()

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

@SuppressLint("MissingPermission")
private fun startLocationUpdatesHMS(
    fusedLocationClientHMS: com.huawei.hms.location.FusedLocationProviderClient,
    locationCallbackHMS: com.huawei.hms.location.LocationCallback
) {
     val locationRequestHMS = com.huawei.hms.location.LocationRequest.create().apply {
        interval = TimeUnit.SECONDS.toMillis(1)
        maxWaitTime = TimeUnit.SECONDS.toMillis(1)
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    fusedLocationClientHMS.requestLocationUpdates(
        locationRequestHMS,
        locationCallbackHMS,
        Looper.getMainLooper()
    )
}

fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val address: Address = addresses[0]
            val cityName: String = address.locality ?: ""
            val countryName: String = address.countryName ?: ""
            return "$cityName, $countryName"
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "Location Not Found"
}

fun isGooglePlayServicesAvailable(context: Context): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)

    return resultCode == ConnectionResult.SUCCESS
}