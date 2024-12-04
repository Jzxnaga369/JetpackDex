package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceIdUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(): String {
        return repository.getDeviceId()
    }
}