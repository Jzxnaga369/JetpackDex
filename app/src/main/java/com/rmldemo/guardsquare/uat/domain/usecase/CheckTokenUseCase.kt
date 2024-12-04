package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Token
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(): Flow<Resource<Token>> {
        return repository.checkToken()
    }
}