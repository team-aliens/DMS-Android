package team.aliens.dms_android.feature.feature.main.announcements;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

import team.aliens.dms_android.feature.feature.notice.NoticeDetailsViewModel;
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase;

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
public final class NoticeDetailsViewModel_Factory implements Factory<NoticeDetailsViewModel> {
  private final Provider<FetchNoticeDetailsUseCase> fetchNoticeDetailsUseCaseProvider;

  public NoticeDetailsViewModel_Factory(
      Provider<FetchNoticeDetailsUseCase> fetchNoticeDetailsUseCaseProvider) {
    this.fetchNoticeDetailsUseCaseProvider = fetchNoticeDetailsUseCaseProvider;
  }

  @Override
  public NoticeDetailsViewModel get() {
    return newInstance(fetchNoticeDetailsUseCaseProvider.get());
  }

  public static NoticeDetailsViewModel_Factory create(
      Provider<FetchNoticeDetailsUseCase> fetchNoticeDetailsUseCaseProvider) {
    return new NoticeDetailsViewModel_Factory(fetchNoticeDetailsUseCaseProvider);
  }

  public static NoticeDetailsViewModel newInstance(
      FetchNoticeDetailsUseCase fetchNoticeDetailsUseCase) {
    return new NoticeDetailsViewModel(fetchNoticeDetailsUseCase);
  }
}
