package team.aliens.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.domain._repository.MealRepository
import team.aliens.domain.usecase.meal.FetchMealsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideRemoteMealUseCase(
        mealRepository: MealRepository
    ): FetchMealsUseCase = FetchMealsUseCase(
        mealRepository = mealRepository,
    )
}