package team.aliens.di.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.local.datastore.common.DataStoreProperty
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreProperty.DataStore.Auth,
    )

    @Provides
    @Singleton
    fun providePreferenceDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return context.authDataStore
    }
}
