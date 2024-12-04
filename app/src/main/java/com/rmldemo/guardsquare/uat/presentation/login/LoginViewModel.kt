package com.rmldemo.guardsquare.uat.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.LoginUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    var state by mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.OnLogin -> {
                login()
            }
            is LoginEvent.OnEmailChange -> {
                changeEmail(event.email)
            }
            is LoginEvent.OnPasswordChange -> {
                changePassword(event.password)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase.invoke(state.email.text, state.password.text)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { version ->
                                state = state.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                isSuccess = false,
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

    private fun changeEmail(email: TextFieldValue) {
        state = state.copy(
            email = email
        )
    }

    private fun changePassword(password: TextFieldValue) {
        state = state.copy(
            password = password
        )
    }
}