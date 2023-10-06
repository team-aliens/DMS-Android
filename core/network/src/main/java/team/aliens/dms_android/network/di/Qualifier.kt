package team.aliens.dms_android.network.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueHttpClient
