package team.aliens.dms.android.core.network.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class GlobalHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpLoggingInterceptor

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class GlobalHttpLoggingInterceptor

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpLoggingLevel

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class GlobalHttpLoggingLevel

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRetrofitClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class GlobalRetrofitClient
