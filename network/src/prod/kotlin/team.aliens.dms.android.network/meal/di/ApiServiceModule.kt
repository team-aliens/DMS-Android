package team.aliens.dms.android.network.meal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.meal.apiservice.MealApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideMealApiService(@GlobalRetrofitClient retrofit: Retrofit): MealApiService =
        retrofit.create(MealApiService::class.java)
}
