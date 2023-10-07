package team.aliens.network.annotation

import javax.inject.Qualifier

/**
 * An annotation class represents injecting, or need to be injected base url
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

/**
 * An annotation class represents injecting, or need to be injected token reissue url
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueUrl

/**
 * An annotation class represents injecting, or need to be injected default HttpLoggingInterceptor
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpLoggingInterceptor

/**
 * An annotation class represents injecting, or need to be injected default http logging level
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpLoggingLevel
