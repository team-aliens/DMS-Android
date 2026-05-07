package team.aliens.dms.android.core.theme.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.theme.datastore.store.ThemeStore
import team.aliens.dms.android.core.theme.datastore.store.ThemeStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindThemeStore(impl: ThemeStoreImpl): ThemeStore
}