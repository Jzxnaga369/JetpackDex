package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Promo
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPromoUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(): Flow<Resource<List<Promo>>> {
        return repository.getPromos()
    }
}