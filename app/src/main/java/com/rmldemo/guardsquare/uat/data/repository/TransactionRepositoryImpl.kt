package com.rmldemo.guardsquare.uat.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.rmldemo.guardsquare.uat.domain.model.Service
import com.rmldemo.guardsquare.uat.domain.model.Transaction
import com.rmldemo.guardsquare.uat.domain.model.TransactionType
import com.rmldemo.guardsquare.uat.domain.repostory.TransactionRepository
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : TransactionRepository {
    override fun getServices(): Flow<Resource<List<Service>>> = flow {
        emit(Resource.Loading())
        try {
            val user = auth.currentUser
            user?.let {
                val document = firestore
                    .collection("services")
                    .get()
                    .await()
                emit(Resource.Success(document.documents.map {
                    Service(
                        id = it.id,
                        name = it["name"] as String,
                        imageUrl = it["imageUrl"] as String,
                        amount = it["amount"] as Long
                    )
                }))
            } ?: kotlin.run {
                emit(Resource.Error("Get History Failed"))
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

    override fun transaction(transaction: Transaction): Flow<Resource<String>> = flow {
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
                val newBalance = if (transaction.type == TransactionType.PLUS) {
                    balance + transaction.amount
                } else {
                    balance - transaction.amount
                }
                if (newBalance < 0) {
                    emit(Resource.Error("Your Balance Not Enough"))
                } else {
                    val dataTransaction = hashMapOf(
                        "userId" to it.uid,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "name" to transaction.name,
                        "amount" to transaction.amount,
                        "imageUrl" to transaction.imageUrl,
                        "type" to transaction.type.toString().lowercase(Locale.getDefault()),
                    )
                    firestore.collection("transactions").add(dataTransaction).await()

                    firestore.collection("users").document(it.uid)
                        .set(hashMapOf("balance" to newBalance)).await()

                    emit(Resource.Success("Transaction Success"))
                }
            } ?: kotlin.run {
                emit(Resource.Error("Transaction Failed"))
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

    override fun getHistories(): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading())
        try {
            val user = auth.currentUser
            user?.let {
                val document = firestore
                    .collection("transactions")
                    .whereEqualTo("userId", it.uid)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .get()
                    .await()
                emit(Resource.Success(document.documents.map { doc ->
                    Transaction(
                        id = doc.id,
                        userId = doc["userId"] as String,
                        createdAt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(doc.getTimestamp("createdAt")?.toDate()),
                        name = doc["name"] as String,
                        imageUrl = doc["imageUrl"] as String,
                        type = if ((doc["type"] as String) == "plus") TransactionType.PLUS else TransactionType.MIN,
                        amount = doc["amount"] as Long
                    )
                }))
            } ?: kotlin.run {
                Log.e("HISTORY", "Get History Failed")
                emit(Resource.Error("Get History Failed"))
            }
        } catch (e: Exception) {
            Log.e("HISTORY", e.message.toString())
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