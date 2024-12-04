package com.rmldemo.guardsquare.uat.presentation

import android.content.SharedPreferences
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rmldemo.guardsquare.uat.MyApp
import com.rmldemo.guardsquare.uat.navigation.NavigationItem
import com.rmldemo.guardsquare.uat.navigation.Screen
import com.rmldemo.guardsquare.uat.presentation.about.AboutScreen
import com.rmldemo.guardsquare.uat.presentation.attestation.AppAttestationScreen
import com.rmldemo.guardsquare.uat.presentation.audio.AudioScreen
import com.rmldemo.guardsquare.uat.presentation.history.HistoryScreen
import com.rmldemo.guardsquare.uat.presentation.home.HomeScreen
import com.rmldemo.guardsquare.uat.presentation.login.LoginScreen
import com.rmldemo.guardsquare.uat.presentation.map.MapScreen
import com.rmldemo.guardsquare.uat.presentation.nfc.NfcScreen
import com.rmldemo.guardsquare.uat.presentation.notification.NotificationScreen
import com.rmldemo.guardsquare.uat.presentation.payment.PaymentScreen
import com.rmldemo.guardsquare.uat.presentation.profile.ProfileScreen
import com.rmldemo.guardsquare.uat.presentation.qr.QrScreen
import com.rmldemo.guardsquare.uat.presentation.register.RegisterScreen
import com.rmldemo.guardsquare.uat.presentation.scanner.ScannerScreen
import com.rmldemo.guardsquare.uat.presentation.splash.SplashScreen
import com.rmldemo.guardsquare.uat.presentation.topup.TopUpScreen
import com.rmldemo.guardsquare.uat.presentation.transfer.TransferScreen
import com.rmldemo.guardsquare.uat.presentation.video.VideoScreen

@Composable
fun EWalletApp(
    sharedPreferences: SharedPreferences,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val listNavigationItem = listOf(
        NavigationItem(Screen.Home.route, "Home", Icons.Default.Home),
        NavigationItem(Screen.History.route, "History", Icons.Default.Description),
        NavigationItem(Screen.Scanner.route, "Scanner", Icons.Default.QrCodeScanner),
        NavigationItem(Screen.Card.route, "Card", Icons.Default.CreditCard),
        NavigationItem(Screen.Profile.route, "Profile", Icons.Default.Person),
    )
    val bottomBarRoutes = setOf(
        Screen.Home.route,
        Screen.History.route,
        Screen.Scanner.route,
        Screen.Card.route,
        Screen.Profile.route,
    )
    val snackbarHostState = remember { SnackbarHostState() }

    var tagId by remember { mutableStateOf<String?>(null) }

    (LocalContext.current.applicationContext as MyApp).updateTagId = { newTagId ->
        tagId = newTagId
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    listNavigationItem.forEachIndexed { index, item ->
                        if (index != 2) {
                            NavigationBarItem(
                                icon = { Icon(item.icon, contentDescription = null) },
                                label = { Text(item.label) },
                                selected = currentRoute == item.route,
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        } else {
                            FloatingActionButton(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(bottom = 8.dp),
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                },
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 0.dp,
                                    pressedElevation = 0.dp,
                                    focusedElevation = 0.dp,
                                    hoveredElevation = 0.dp
                                )
                            ) {
                                Icon(Icons.Filled.QrCodeScanner, null)
                            }
                        }
                    }
                }
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(it),
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    sharedPreferences = sharedPreferences,
                    navigateToLoginScreen = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToHomeScreen = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToHomeScreen = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToRegisterScreen = {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    snackbarHostState = snackbarHostState,
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateLoginScreen = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToHomeScreen = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    snackbarHostState = snackbarHostState,
                )
            }
            composable(Screen.Home.route) {
                (LocalContext.current.applicationContext as MyApp).updateTagId?.invoke(null)
                HomeScreen(
                    navigateToTopUpScreen = {
                        navController.navigate(Screen.TopUp.route)
                    },
                    navigateToTransferScreen = {
                        navController.navigate(Screen.Transfer.route)
                    },
                    navigateToPaymentScreen = {service ->
                        navController.navigate(Screen.Payment.createRoute(service))
                    },
                    navigateToQrScreen = {
                        navController.navigate(Screen.Qr.route)
                    },
                    navigateToNotificationScreen = {
                        navController.navigate(Screen.Notification.route)
                    },
                )
            }
            composable(Screen.History.route) {
                (LocalContext.current.applicationContext as MyApp).updateTagId?.invoke(null)
                HistoryScreen()
            }
            composable(Screen.Card.route) {
                NfcScreen(tagId)
            }
            composable(Screen.Profile.route) {
                (LocalContext.current.applicationContext as MyApp).updateTagId?.invoke(null)
                ProfileScreen(
                    navigateToMapScreen = {
                        navController.navigate(Screen.Map.route)
                    },
                    navigateToAudioScreen = {
                        navController.navigate(Screen.Audio.route)
                    },
                    navigateToVideoScreen = {
                        navController.navigate(Screen.Video.route)
                    },
                    navigateToLoginScreen = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToAboutScreen = {
                        navController.navigate(Screen.About.route) {
                            navController.navigate(Screen.About.route)
                        }
                    },
                    navigateToAppAttestationScreen = {
                        navController.navigate(Screen.AppAttestation.route) {
                            navController.navigate(Screen.AppAttestation.route)
                        }
                    }
                )
            }
            composable(Screen.TopUp.route) {
                TopUpScreen(
                    navigateBack = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Transfer.route) {
                TransferScreen(
                    navigateBack = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Payment.route) {
                PaymentScreen(
                    navigateBack = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Qr.route) {
                QrScreen(
                    navigateBack = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Scanner.route) {
                ScannerScreen(snackbarHostState)
            }
            composable(Screen.Notification.route) {
                NotificationScreen(
                    navigateBack = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Map.route) {
                MapScreen(
                    navigateBack = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Audio.route) {
                AudioScreen(
                    navigateBack = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Video.route) {
                VideoScreen(
                    navigateBack = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(
                    navigateBack = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.AppAttestation.route) {
                AppAttestationScreen(
                    navigateBack = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    }
}