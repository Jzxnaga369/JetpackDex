package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.repostory.AuthRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<String>> {
        return repository.login(email, password)
    }
}