package team.aliens.dms.android.data.outing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.outing.repository.OutingRepository
import team.aliens.dms.android.data.outing.repository.OutingRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOutingRepository(impl: OutingRepositoryImpl): OutingRepository
}
