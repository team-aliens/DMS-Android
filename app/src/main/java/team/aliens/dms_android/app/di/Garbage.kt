package team.aliens.dms_android.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.data.datasource.local.LocalSchoolDataSource
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.data.manager.TokenReissueManager
import team.aliens.dms_android.network.annotation.TokenReissueHttpClient
import team.aliens.dms_android.network.annotation.TokenReissueUrl
import team.aliens.network.http.IgnoreRequestWrapper
import team.aliens.network.http.TokenReissueManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GarbageModule {

    // TODO: remove original class
    @Provides
    @Singleton
    @team.aliens.dms_android.network.annotation.TokenReissueHttpClient
    fun provideTokenReissueManager(
        @team.aliens.dms_android.network.annotation.TokenReissueUrl tokenReissueUrl: String,
    ): TokenReissueManager = TokenReissueManagerImpl(
        reissueUrl = tokenReissueUrl,
    )

    // TODO: remove original class
    @Provides
    @Singleton
    fun provideIgnoreRequestWrapper(): IgnoreRequestWrapper = IgnoreRequestWrapper

    // TODO: remove original class
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
