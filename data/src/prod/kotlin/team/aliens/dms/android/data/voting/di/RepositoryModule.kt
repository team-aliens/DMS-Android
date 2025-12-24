package team.aliens.dms.android.data.voting.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.voting.repository.VotingRepository
import team.aliens.dms.android.data.voting.repository.VotingRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVotingRepository(impl: VotingRepositoryImpl): VotingRepository
}
