package com.rmldemo.guardsquare.uat.presentation.attestation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.CheckTokenUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppAttestationViewModel @Inject constructor(
    private val checkTokenUseCase: CheckTokenUseCase
) : ViewModel() {
    var state by mutableStateOf(AppAttestationState())

    fun onEvent(event: AppAttestationEvent) {
        when(event) {
            is AppAttestationEvent.CheckToken -> {
                checkToken()
            }
        }
    }

    private fun checkToken() {
        viewModelScope.launch {
            checkTokenUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { token ->
                                state = state.copy(
                                    isLoading = false,
                                    token = token,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                message = result.message.toString()
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading,
                            )
                        }
                    }
                }
        }
    }
}