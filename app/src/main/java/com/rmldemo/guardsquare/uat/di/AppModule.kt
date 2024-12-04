package com.rmldemo.guardsquare.uat.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rmldemo.guardsquare.uat.data.local.AppDatabase
import com.rmldemo.guardsquare.uat.data.remote.ApiService
import com.rmldemo.guardsquare.uat.data.repository.AuthRepositoryImpl
import com.rmldemo.guardsquare.uat.data.repository.InformationRepositoryImpl
import com.rmldemo.guardsquare.uat.data.repository.TransactionRepositoryImpl
import com.rmldemo.guardsquare.uat.domain.repostory.AuthRepository
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.domain.repostory.TransactionRepository
import com.rmldemo.guardsquare.uat.utils.Constans
import com.rmldemo.guardsquare.uat.utils.Constans.DATA_STORE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constans.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(DATA_STORE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore, storage: FirebaseStorage): AuthRepository = AuthRepositoryImpl(auth, firestore, storage)

    @Provides
    @Singleton
    fun provideInformationRepository(apiService: ApiService, database: AppDatabase, sharedPreferences: SharedPreferences, @ApplicationContext context: Context): InformationRepository = InformationRepositoryImpl(apiService, database.notificationDao, sharedPreferences, context)

    @Provides
    @Singleton
    fun provideTransactionRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): TransactionRepository = TransactionRepositoryImpl(auth, firestore)
}