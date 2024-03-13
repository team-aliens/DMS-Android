package team.aliens.dms.android.data.studyroom.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepository
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStudyRoomRepository(impl: StudyRoomRepositoryImpl): StudyRoomRepository
}
