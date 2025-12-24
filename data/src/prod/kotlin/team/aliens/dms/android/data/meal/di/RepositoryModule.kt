package team.aliens.dms.android.data.meal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import team.aliens.dms.android.data.meal.repository.MealRepository
import team.aliens.dms.android.data.meal.repository.MealRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(dagger.hilt.components.SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository
}
