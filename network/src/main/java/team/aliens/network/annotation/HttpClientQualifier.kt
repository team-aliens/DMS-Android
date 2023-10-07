package team.aliens.network.annotation

import javax.inject.Qualifier

/**
 * An annotation class represents injecting, or need to be injected global OkHttpClient
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpClient

/**
 * An annotation class represents injecting, or need to be injected token reissue OkHttpClient
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueHttpClient
