package team.aliens.di.facade

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.data.datasource.local.LocalSchoolDataSource
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.data.manager.TokenReissueManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FacadeModule {

    @Provides
    @Singleton
    fun provideAuthorizationFacade(
        localAuthDataSource: LocalAuthDataSource,
        localSchoolDataSource: LocalSchoolDataSource,
        tokenReissueManager: TokenReissueManager,
    ): AuthorizationFacade {
        return AuthorizationFacade(
            localAuthDataSource = localAuthDataSource,
            localSchoolDataSource = localSchoolDataSource,
            tokenReissueManager = tokenReissueManager,
        )
    }
}
