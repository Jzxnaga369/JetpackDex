package com.rmldemo.guardsquare.uat.presentation.home

sealed class HomeEvent {
    data object GetUser: HomeEvent()
    data object GetService: HomeEvent()
    data object GetPromo: HomeEvent()
}