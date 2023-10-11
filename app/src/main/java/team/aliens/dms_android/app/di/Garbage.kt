package team.aliens.dms_android.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.jwt.di.TokenReissueUrl
import team.aliens.dms_android.data.datasource.local.LocalAuthDataSource
import team.aliens.dms_android.data.datasource.local.LocalSchoolDataSource
import team.aliens.dms_android.data.facade.AuthorizationFacade
import team.aliens.dms_android.data.manager.TokenReissueManager
import team.aliens.dms_android.network.http.IgnoreRequestWrapper
import team.aliens.dms_android.network.http.TokenReissueManagerImpl
import javax.inject.Singleton


// TODO: remove
@Deprecated("No usage")
@Module
@InstallIn(SingletonComponent::class)
object GarbageModule {

    // TODO: remove original class
    @Provides
    @Singleton
    fun provideTokenReissueManager(
        @TokenReissueUrl tokenReissueUrl: String,
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
