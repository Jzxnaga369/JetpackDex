package com.rmldemo.guardsquare.uat.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.GetPromoUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.GetServiceUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.GetUserUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getServiceUseCase: GetServiceUseCase,
    private val getPromoUseCase: GetPromoUseCase,
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    init {
        onEvent(HomeEvent.GetUser)
        onEvent(HomeEvent.GetService)
        onEvent(HomeEvent.GetPromo)
    }

    private fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUser -> {
                getUser()
            }
            HomeEvent.GetPromo -> {
                getPromo()
            }
            HomeEvent.GetService -> {
                getService()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { user ->
                                state = state.copy(
                                    isLoadingUser = false,
                                    user = user,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingUser = false,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingUser = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun getService() {
        viewModelScope.launch {
            getServiceUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { services ->
                                state = state.copy(
                                    isLoadingService = false,
                                    services = services,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingService = false,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingService = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun getPromo() {
        viewModelScope.launch {
            getPromoUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { promos ->
                                state = state.copy(
                                    isLoadingPromo = false,
                                    promos = promos,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingPromo = false,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingPromo = result.isLoading,
                            )
                        }
                    }
                }
        }
    }
}