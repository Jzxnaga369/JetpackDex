package com.rmldemo.guardsquare.uat.domain.repostory

import android.net.Uri
import com.rmldemo.guardsquare.uat.domain.model.User
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUser(): Flow<Resource<User>>
    fun login(email: String, password: String): Flow<Resource<String>>
    fun register(name: String, email: String, password: String): Flow<Resource<String>>
    fun uploadPhoto(photo: Uri): Flow<Resource<User>>
    fun logout(): Flow<Resource<Boolean>>
}