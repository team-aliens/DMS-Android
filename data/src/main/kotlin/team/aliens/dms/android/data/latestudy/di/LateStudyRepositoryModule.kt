package team.aliens.dms.android.data.latestudy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepositoryImpl
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LateStudyRepositoryModule {

    @Binds
    abstract fun bindLateStudyRepository(
        lateStudyRepositoryImpl: LateStudyRepositoryImpl,
    ): LateStudyRepository
}
