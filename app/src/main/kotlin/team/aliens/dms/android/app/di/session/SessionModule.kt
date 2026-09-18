package team.aliens.dms.android.app.di.session

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.app.session.AppSessionCleaner
import team.aliens.dms.android.core.jwt.SessionCleaner
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionCleaner(impl: AppSessionCleaner): SessionCleaner
}
