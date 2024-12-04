package com.rmldemo.guardsquare.uat.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rmldemo.guardsquare.uat.domain.model.User
import com.rmldemo.guardsquare.uat.domain.repostory.AuthRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {
    override fun getUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val user = auth.currentUser
            user?.let {
                val document = firestore
                    .collection("users")
                    .document(it.uid)
                    .get()
                    .await()

                val balance = document["balance"] as Long
                emit(
                    Resource.Success(User(
                    it.uid,
                    it.displayName ?: "",
                    it.email ?: "",
                    it.photoUrl.toString(),
                    balance
                )))
            } ?: kotlin.run {
                emit(Resource.Error("Get User Failed"))
            }
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    override fun login(email: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                emit(Resource.Success("Login Success"))
            } ?: kotlin.run {
                emit(Resource.Success("Login Failed"))
            }
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun register(name: String, email: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                it.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build()).await()
                firestore.collection("users").document(it.uid).set(hashMapOf("balance" to 0)).await()
                emit(Resource.Success("Register Success"))
            } ?: kotlin.run {
                emit(Resource.Success("Register Failed"))
            }
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun uploadPhoto(photo: Uri): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val path = "users/" + UUID.randomUUID().toString()
            storage.reference.child(path).putFile(photo).await()
            val downloadUri = storage.reference.child(path).downloadUrl.await()
            auth.currentUser?.let {
                val document = firestore
                    .collection("users")
                    .document(it.uid)
                    .get()
                    .await()

                val balance = document["balance"] as Long
                it.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build()).await()
                emit(
                    Resource.Success(User(
                    it.uid,
                    it.displayName ?: "",
                    it.email ?: "",
                    downloadUri.toString(),
                    balance
                )))
            } ?: kotlin.run {
                emit(Resource.Error("Upload Photo Failed"))
            }
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun logout(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            auth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }
}