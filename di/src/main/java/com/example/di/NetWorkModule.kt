package com.example.di

import com.example.data.remote.api.MealApi
import com.example.data.remote.api.MyPageApi
import com.example.data.remote.api.NoticeApi
import com.example.data.remote.api.SchoolsApi
import com.example.data.remote.api.StudentsApi
import com.example.data.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private const val BASE_URL = "http://google.com/"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpclient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

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

    @Provides
    fun provideNoticeApi(retrofit: Retrofit): NoticeApi =
        retrofit.create(NoticeApi::class.java)

    @Provides
    fun provideSchoolsApi(retrofit: Retrofit): SchoolsApi =
        retrofit.create(SchoolsApi::class.java)

    @Provides
    fun provideMyPageApi(retrofit: Retrofit): MyPageApi =
        retrofit.create(MyPageApi::class.java)
}
