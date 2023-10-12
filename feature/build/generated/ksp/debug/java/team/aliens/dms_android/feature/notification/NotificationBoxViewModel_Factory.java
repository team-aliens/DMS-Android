package team.aliens.dms_android.feature.notification;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.dms_android.domain.usecase.notification.FetchNotificationsUseCase;

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
public final class NotificationBoxViewModel_Factory implements Factory<NotificationBoxViewModel> {
  private final Provider<FetchNotificationsUseCase> fetchNotificationsUseCaseProvider;

  public NotificationBoxViewModel_Factory(
      Provider<FetchNotificationsUseCase> fetchNotificationsUseCaseProvider) {
    this.fetchNotificationsUseCaseProvider = fetchNotificationsUseCaseProvider;
  }

  @Override
  public NotificationBoxViewModel get() {
    return newInstance(fetchNotificationsUseCaseProvider.get());
  }

  public static NotificationBoxViewModel_Factory create(
      Provider<FetchNotificationsUseCase> fetchNotificationsUseCaseProvider) {
    return new NotificationBoxViewModel_Factory(fetchNotificationsUseCaseProvider);
  }

  public static NotificationBoxViewModel newInstance(
      FetchNotificationsUseCase fetchNotificationsUseCase) {
    return new NotificationBoxViewModel(fetchNotificationsUseCase);
  }
}
