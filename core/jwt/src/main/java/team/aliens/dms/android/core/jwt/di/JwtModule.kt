package team.aliens.dms.android.core.jwt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.android.core.jwt.JwtProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JwtModule {

    @IsJwtAvailable
    @Provides
    @Singleton
    fun provideWhetherJwtAvailable(
        jwtProvider: JwtProvider,
    ): StateFlow<Boolean> = jwtProvider.isCachedRefreshTokenAvailable
}
