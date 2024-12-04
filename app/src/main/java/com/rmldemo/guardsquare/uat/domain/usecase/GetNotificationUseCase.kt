package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.Notification
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(): Flow<List<Notification>> {
        return repository.getNotifications()
    }
}