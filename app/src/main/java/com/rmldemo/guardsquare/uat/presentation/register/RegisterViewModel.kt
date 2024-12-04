package com.rmldemo.guardsquare.uat.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.RegisterUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    var state by mutableStateOf(RegisterState())

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.OnRegister -> {
                register()
            }
            is RegisterEvent.OnNameChange -> {
                changeName(event.name)
            }
            is RegisterEvent.OnEmailChange -> {
                changeEmail(event.email)
            }
            is RegisterEvent.OnPasswordChange -> {
                changePassword(event.password)
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            registerUseCase.invoke(state.name.text, state.email.text, state.password.text)
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

    private fun changeName(name: TextFieldValue) {
        state = state.copy(
            name = name
        )
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