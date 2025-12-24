package team.aliens.dms.android.data.remains.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.remains.repository.RemainsRepository
import team.aliens.dms.android.data.remains.repository.RemainsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemainsRepository(impl: RemainsRepositoryImpl): RemainsRepository
}
