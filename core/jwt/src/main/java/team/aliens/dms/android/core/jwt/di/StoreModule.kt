package team.aliens.dms.android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.jwt.datastore.store.JwtStore
import team.aliens.dms.android.core.jwt.datastore.store.JwtStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindJwtStore(impl: JwtStoreImpl): JwtStore
}
