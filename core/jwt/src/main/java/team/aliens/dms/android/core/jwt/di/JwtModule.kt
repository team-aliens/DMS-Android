package team.aliens.dms.android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.jwt.store.JwtStore
import team.aliens.dms.android.core.jwt.store.JwtStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class JwtModule {

    @Binds
    @Singleton
    abstract fun bindJwtStore(impl: JwtStoreImpl): JwtStore
}
