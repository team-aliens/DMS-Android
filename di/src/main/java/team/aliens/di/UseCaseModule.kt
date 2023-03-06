package team.aliens.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.domain.repository.MealRepository
import team.aliens.domain.usecase.meal.RemoteMealUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun bindRemoteMealUseCase(
        mealRepository: MealRepository
    ): RemoteMealUseCase = RemoteMealUseCase(
        mealRepository = mealRepository,
    )
}