package com.rmldemo.guardsquare.uat.presentation.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.GetUserUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.GetVersionUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.LogoutUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.UploadPhotoUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getVersionUseCase: GetVersionUseCase,
    private val changePhotoUseCase: UploadPhotoUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    var state by mutableStateOf(ProfileState())

    init {
        onEvent(ProfileEvent.OnGetUser)
        onEvent(ProfileEvent.OnGetVersion)
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnChangePhoto -> {
                changePhoto(event.uri)
            }
            ProfileEvent.OnGetUser -> {
                getUser()
            }
            ProfileEvent.OnGetVersion -> {
                getVersion()
            }
            ProfileEvent.OnLogout -> {
                logout()
            }
            is ProfileEvent.OnPickImage -> {
                pickImage(event.isOpen)
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

    private fun getVersion() {
        viewModelScope.launch {
            getVersionUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { version ->
                                state = state.copy(
                                    isLoadingVersion = false,
                                    version = "Api Verison : " + version.version
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingVersion = false,
                                version = "Api Verison : " + "Error Get Version"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingVersion = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().collect {}
        }
    }

    private fun changePhoto(newPhoto: Uri) {
        onEvent(ProfileEvent.OnPickImage(false))
        viewModelScope.launch {
            changePhotoUseCase.invoke(newPhoto)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { user ->
                                state = state.copy(
                                    user = user,
                                    isLoadingUploadPhoto = false,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingUploadPhoto = false,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingUploadPhoto = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun pickImage(isOpen: Boolean) {
        state = state.copy(
            pickImage = isOpen,
        )
    }
}