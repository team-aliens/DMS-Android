package team.aliens.dms.android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.JwtProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProviderModule {

    @Binds
    @Singleton
    abstract fun bindJwtProvider(impl: JwtProviderImpl): JwtProvider
}
