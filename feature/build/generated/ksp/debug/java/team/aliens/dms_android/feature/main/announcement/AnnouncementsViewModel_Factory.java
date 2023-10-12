package team.aliens.dms_android.feature.main.announcement;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.dms_android.domain.usecase.notice.FetchNoticesUseCase;

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
public final class AnnouncementsViewModel_Factory implements Factory<AnnouncementsViewModel> {
  private final Provider<FetchNoticesUseCase> fetchNoticesUseCaseProvider;

  public AnnouncementsViewModel_Factory(Provider<FetchNoticesUseCase> fetchNoticesUseCaseProvider) {
    this.fetchNoticesUseCaseProvider = fetchNoticesUseCaseProvider;
  }

  @Override
  public AnnouncementsViewModel get() {
    return newInstance(fetchNoticesUseCaseProvider.get());
  }

  public static AnnouncementsViewModel_Factory create(
      Provider<FetchNoticesUseCase> fetchNoticesUseCaseProvider) {
    return new AnnouncementsViewModel_Factory(fetchNoticesUseCaseProvider);
  }

  public static AnnouncementsViewModel newInstance(FetchNoticesUseCase fetchNoticesUseCase) {
    return new AnnouncementsViewModel(fetchNoticesUseCase);
  }
}
