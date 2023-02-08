package team.aliens.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.aliens.data.intercepter.AuthorizationInterceptor
import team.aliens.data.remote.api.*

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    private const val BASE_URL = "http://3.39.162.197:8080/"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpclient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
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

    @Provides
    fun provideStudyRoomApi(retrofit: Retrofit): StudyRoomApi =
        retrofit.create(StudyRoomApi::class.java)
}
