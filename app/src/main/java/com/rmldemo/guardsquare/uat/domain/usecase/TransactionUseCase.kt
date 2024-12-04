package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Transaction
import com.rmldemo.guardsquare.uat.domain.repostory.TransactionRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(transaction: Transaction): Flow<Resource<String>> {
        return repository.transaction(transaction)
    }
}