package team.aliens.dms.android.data.point.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.point.repository.PointRepository
import team.aliens.dms.android.data.point.repository.PointRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPointRepository(impl: PointRepositoryImpl): PointRepository
}
