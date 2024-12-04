package com.rmldemo.guardsquare.uat.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)