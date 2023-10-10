package team.aliens.dms_android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.jwt.datastore.JwtStore
import team.aliens.dms_android.core.jwt.datastore.JwtStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class JwtModule {

    @Binds
    @Singleton
    abstract fun bindJwtStore(impl: JwtStoreImpl): JwtStore
}
