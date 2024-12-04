package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Version
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVersionUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(): Flow<Resource<Version>> {
        return repository.getVersion()
    }
}