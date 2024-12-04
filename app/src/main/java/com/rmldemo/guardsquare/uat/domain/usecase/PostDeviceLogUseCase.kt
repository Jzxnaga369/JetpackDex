package com.rmldemo.guardsquare.uat.domain.usecase

import com.rmldemo.guardsquare.uat.domain.model.DeviceLog
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDeviceLogUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    operator fun invoke(deviceLog: DeviceLog): Flow<Resource<DeviceLog>> {
        return repository.postDeviceLog(deviceLog)
    }
}