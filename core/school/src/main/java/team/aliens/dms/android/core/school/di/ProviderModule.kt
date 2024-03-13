package team.aliens.dms.android.core.school.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.core.school.SchoolProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProviderModule {

    @Binds
    @Singleton
    abstract fun bindSchoolProvider(impl: SchoolProviderImpl): SchoolProvider
}
