package com.example.di

import com.example.auth_data.remote.api.StudentsApi
import com.example.auth_data.remote.api.UserApi
import com.example.feature_data.remote.api.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private const val BASE_URL = "BASE_URL"

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideStudentsApi(retrofit: Retrofit): StudentsApi =
        retrofit.create(StudentsApi::class.java)

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    fun provideMealApi(retrofit: Retrofit): MealApi =
        retrofit.create(MealApi::class.java)
}