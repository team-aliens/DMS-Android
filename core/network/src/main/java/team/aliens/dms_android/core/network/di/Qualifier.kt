package team.aliens.dms_android.core.network.di

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
annotation class TokenReissueHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FileUploadHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpLoggingInterceptor
