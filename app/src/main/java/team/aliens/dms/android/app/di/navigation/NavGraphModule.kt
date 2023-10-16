package team.aliens.dms.android.app.di.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.feature.NavGraph
import team.aliens.dms.android.feature.NavGraphs
import javax.inject.Singleton

// TODO: 추후 확인 필요
@Module
@InstallIn(SingletonComponent::class)
object NavGraphModule {

    @Provides
    @Singleton
    @RootNavGraph
    fun provideRootNavGraph(): NavGraph = NavGraphs.root
}
