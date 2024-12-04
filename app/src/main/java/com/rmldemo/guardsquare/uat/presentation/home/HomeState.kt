package com.rmldemo.guardsquare.uat.presentation.home

import com.rmldemo.guardsquare.uat.domain.model.Promo
import com.rmldemo.guardsquare.uat.domain.model.Service
import com.rmldemo.guardsquare.uat.domain.model.User

data class HomeState(
    val isLoadingUser: Boolean = false,
    val isLoadingService: Boolean = false,
    val isLoadingPromo: Boolean = false,
    val user: User = User("", "", "", "", 0),
    val services: List<Service> = listOf(),
    val promos: List<Promo> = listOf()
)
