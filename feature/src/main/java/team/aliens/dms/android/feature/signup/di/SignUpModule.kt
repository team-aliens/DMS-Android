package team.aliens.dms.android.feature.signup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.aliens.dms.android.feature.signup.SignUpViewModel
import team.aliens.dms.android.feature.signup.SignUpViewModelImpl

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SignUpModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpViewModel(impl: SignUpViewModelImpl): SignUpViewModel
}
