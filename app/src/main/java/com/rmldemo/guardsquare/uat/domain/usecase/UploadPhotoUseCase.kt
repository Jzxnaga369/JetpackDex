package com.rmldemo.guardsquare.uat.domain.usecase

import android.net.Uri
import com.rmldemo.guardsquare.uat.domain.model.User
import com.rmldemo.guardsquare.uat.domain.repostory.AuthRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(photo: Uri): Flow<Resource<User>> {
        return repository.uploadPhoto(photo)
    }
}