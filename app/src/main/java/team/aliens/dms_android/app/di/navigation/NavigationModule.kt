package team.aliens.dms_android.app.di.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.feature.NavGraph
import team.aliens.dms_android.feature.NavGraphs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    @RootNavGraph
    fun provideRootNavGraph(): NavGraph = NavGraphs.root
}
