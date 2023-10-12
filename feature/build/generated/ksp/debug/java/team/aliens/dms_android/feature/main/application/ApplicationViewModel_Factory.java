package team.aliens.dms_android.feature.main.application;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.dms_android.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase;
import team.aliens.dms_android.domain.usecase.studyroom.FetchCurrentAppliedStudyRoomUseCase;
import team.aliens.dms_android.feature.main.application.ApplicationViewModel;

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
public final class ApplicationViewModel_Factory implements Factory<ApplicationViewModel> {
  private final Provider<FetchCurrentAppliedStudyRoomUseCase> fetchCurrentAppliedStudyRoomUseCaseProvider;

  private final Provider<FetchCurrentAppliedRemainsOptionUseCase> fetchCurrentAppliedRemainsOptionUseCaseProvider;

  public ApplicationViewModel_Factory(
      Provider<FetchCurrentAppliedStudyRoomUseCase> fetchCurrentAppliedStudyRoomUseCaseProvider,
      Provider<FetchCurrentAppliedRemainsOptionUseCase> fetchCurrentAppliedRemainsOptionUseCaseProvider) {
    this.fetchCurrentAppliedStudyRoomUseCaseProvider = fetchCurrentAppliedStudyRoomUseCaseProvider;
    this.fetchCurrentAppliedRemainsOptionUseCaseProvider = fetchCurrentAppliedRemainsOptionUseCaseProvider;
  }

  @Override
  public ApplicationViewModel get() {
    return newInstance(fetchCurrentAppliedStudyRoomUseCaseProvider.get(), fetchCurrentAppliedRemainsOptionUseCaseProvider.get());
  }

  public static ApplicationViewModel_Factory create(
      Provider<FetchCurrentAppliedStudyRoomUseCase> fetchCurrentAppliedStudyRoomUseCaseProvider,
      Provider<FetchCurrentAppliedRemainsOptionUseCase> fetchCurrentAppliedRemainsOptionUseCaseProvider) {
    return new ApplicationViewModel_Factory(fetchCurrentAppliedStudyRoomUseCaseProvider, fetchCurrentAppliedRemainsOptionUseCaseProvider);
  }

  public static ApplicationViewModel newInstance(
      FetchCurrentAppliedStudyRoomUseCase fetchCurrentAppliedStudyRoomUseCase,
      FetchCurrentAppliedRemainsOptionUseCase fetchCurrentAppliedRemainsOptionUseCase) {
    return new ApplicationViewModel(fetchCurrentAppliedStudyRoomUseCase, fetchCurrentAppliedRemainsOptionUseCase);
  }
}
