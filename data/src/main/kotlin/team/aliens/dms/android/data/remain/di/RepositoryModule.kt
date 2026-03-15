package team.aliens.dms.android.data.remain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.remain.repository.RemainsRepository
import team.aliens.dms.android.data.remain.repository.RemainsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemainsRepository(impl: RemainsRepositoryImpl): RemainsRepository
}
