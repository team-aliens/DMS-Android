package team.aliens.dms_android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.jwt.JwtStore
import team.aliens.dms_android.core.jwt.JwtStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class JwtModule {

    @Binds
    @Singleton
    abstract fun bindJwtStore(impl: JwtStoreImpl): JwtStore
}
