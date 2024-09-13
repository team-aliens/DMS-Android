package team.aliens.dms.android.core.datastore

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeaturesDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JwtDataStore
