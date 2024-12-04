package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Service
import com.rmldemo.guardsquare.uat.domain.repostory.TransactionRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServiceUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Resource<List<Service>>> {
        return repository.getServices()
    }
}