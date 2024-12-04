package com.rmldemo.guardsquare.uat.domain.repostory

import com.rmldemo.guardsquare.uat.domain.model.Service
import com.rmldemo.guardsquare.uat.domain.model.Transaction
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getServices(): Flow<Resource<List<Service>>>
    fun transaction(transaction: Transaction): Flow<Resource<String>>
    fun getHistories(): Flow<Resource<List<Transaction>>>
}