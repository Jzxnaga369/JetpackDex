package com.rmldemo.guardsquare.uat.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object History : Screen("history")
    data object Card : Screen("card")
    data object Scanner : Screen("scanner")
    data object Profile : Screen("profile")
    data object TopUp : Screen("top-up")
    data object Transfer : Screen("transfer")
    data object Payment : Screen("payment?service={service}") {
        fun createRoute(service: String) = "payment?service=$service"
    }
    data object Qr : Screen("qr")
    data object Notification : Screen("notification")
    data object Map : Screen("map")
    data object Audio : Screen("audio")
    data object Video : Screen("video")
    data object About : Screen("about")
    data object AppAttestation : Screen("app-attestation")
}