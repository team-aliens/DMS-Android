package team.aliens.dms_android.feature.point;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.dms_android.domain.usecase.point.FetchPointsUseCase;

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
public final class PointHistoryViewModel_Factory implements Factory<PointHistoryViewModel> {
  private final Provider<FetchPointsUseCase> fetchPointsUseCaseProvider;

  public PointHistoryViewModel_Factory(Provider<FetchPointsUseCase> fetchPointsUseCaseProvider) {
    this.fetchPointsUseCaseProvider = fetchPointsUseCaseProvider;
  }

  @Override
  public PointHistoryViewModel get() {
    return newInstance(fetchPointsUseCaseProvider.get());
  }

  public static PointHistoryViewModel_Factory create(
      Provider<FetchPointsUseCase> fetchPointsUseCaseProvider) {
    return new PointHistoryViewModel_Factory(fetchPointsUseCaseProvider);
  }

  public static PointHistoryViewModel newInstance(FetchPointsUseCase fetchPointsUseCase) {
    return new PointHistoryViewModel(fetchPointsUseCase);
  }
}
