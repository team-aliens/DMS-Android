package team.aliens.dms_android.feature.feature.main.home;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.domain.usecase.meal.FetchMealFromLocalOrRemoteIfNotExistsUseCase;
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<FetchWhetherNewNoticesExistUseCase> fetchWhetherNewNoticesExistUseCaseProvider;

  private final Provider<FetchMealFromLocalOrRemoteIfNotExistsUseCase> fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider;

  public HomeViewModel_Factory(
      Provider<FetchWhetherNewNoticesExistUseCase> fetchWhetherNewNoticesExistUseCaseProvider,
      Provider<FetchMealFromLocalOrRemoteIfNotExistsUseCase> fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider) {
    this.fetchWhetherNewNoticesExistUseCaseProvider = fetchWhetherNewNoticesExistUseCaseProvider;
    this.fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider = fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(fetchWhetherNewNoticesExistUseCaseProvider.get(), fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<FetchWhetherNewNoticesExistUseCase> fetchWhetherNewNoticesExistUseCaseProvider,
      Provider<FetchMealFromLocalOrRemoteIfNotExistsUseCase> fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider) {
    return new HomeViewModel_Factory(fetchWhetherNewNoticesExistUseCaseProvider, fetchMealFromLocalOrRemoteIfNotExistsUseCaseProvider);
  }

  public static HomeViewModel newInstance(
      FetchWhetherNewNoticesExistUseCase fetchWhetherNewNoticesExistUseCase,
      FetchMealFromLocalOrRemoteIfNotExistsUseCase fetchMealFromLocalOrRemoteIfNotExistsUseCase) {
    return new HomeViewModel(fetchWhetherNewNoticesExistUseCase, fetchMealFromLocalOrRemoteIfNotExistsUseCase);
  }
}
