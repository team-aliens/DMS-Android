package team.aliens.dms_android.feature.main.mypage;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import team.aliens.dms_android.domain.usecase.auth.SignOutUseCase;
import team.aliens.dms_android.domain.usecase.student.FetchMyPageUseCase;
import team.aliens.dms_android.domain.usecase.student.WithdrawUseCase;

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
public final class MyPageViewModel_Factory implements Factory<MyPageViewModel> {
  private final Provider<FetchMyPageUseCase> fetchMyPageUseCaseProvider;

  private final Provider<SignOutUseCase> signOutUseCaseProvider;

  private final Provider<WithdrawUseCase> withdrawUseCaseProvider;

  public MyPageViewModel_Factory(Provider<FetchMyPageUseCase> fetchMyPageUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<WithdrawUseCase> withdrawUseCaseProvider) {
    this.fetchMyPageUseCaseProvider = fetchMyPageUseCaseProvider;
    this.signOutUseCaseProvider = signOutUseCaseProvider;
    this.withdrawUseCaseProvider = withdrawUseCaseProvider;
  }

  @Override
  public MyPageViewModel get() {
    return newInstance(fetchMyPageUseCaseProvider.get(), signOutUseCaseProvider.get(), withdrawUseCaseProvider.get());
  }

  public static MyPageViewModel_Factory create(
      Provider<FetchMyPageUseCase> fetchMyPageUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<WithdrawUseCase> withdrawUseCaseProvider) {
    return new MyPageViewModel_Factory(fetchMyPageUseCaseProvider, signOutUseCaseProvider, withdrawUseCaseProvider);
  }

  public static MyPageViewModel newInstance(FetchMyPageUseCase fetchMyPageUseCase,
      SignOutUseCase signOutUseCase, WithdrawUseCase withdrawUseCase) {
    return new MyPageViewModel(fetchMyPageUseCase, signOutUseCase, withdrawUseCase);
  }
}
