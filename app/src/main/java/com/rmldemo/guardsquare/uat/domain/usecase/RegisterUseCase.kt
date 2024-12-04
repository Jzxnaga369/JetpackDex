package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.repostory.AuthRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(name: String, email: String, password: String): Flow<Resource<String>> {
        return repository.register(name, email, password)
    }
}