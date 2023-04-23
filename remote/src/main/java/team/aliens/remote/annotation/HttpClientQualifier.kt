package team.aliens.remote.annotation

import javax.inject.Qualifier

/**
 * An annotation class represents injecting, or need to be injected global OkHttpClient
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class GlobalOkHttpClient

/**
 * An annotation class represents injecting, or need to be injected token reissue OkHttpClient
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueOkHttpClient
