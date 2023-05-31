package team.aliens.di.manager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.manager.TokenReissueManager
import team.aliens.remote.http.TokenReissueManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindTokenReissueManager(
        impl: TokenReissueManagerImpl,
    ): TokenReissueManager
}
