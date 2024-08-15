package team.aliens.dms.android.core.datastore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.datastore.DeviceDataStore
import team.aliens.dms.android.core.datastore.FeaturesDataStore
import team.aliens.dms.android.core.datastore.JwtDataStore
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.deviceStore
import team.aliens.dms.android.core.datastore.featuresStore
import team.aliens.dms.android.core.datastore.jwtStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    @JwtDataStore
    fun providePreferenceDataStore(
        @ApplicationContext context: Context,
    ): PreferencesDataStore = context.jwtStore

    @Provides
    @Singleton
    @FeaturesDataStore
    fun provideFeaturesDataStore(
        @ApplicationContext context: Context,
    ): PreferencesDataStore = context.featuresStore

    @Provides
    @Singleton
    @DeviceDataStore
    fun provideDeviceDataStore(
        @ApplicationContext context: Context,
    ): PreferencesDataStore = context.deviceStore
}
