package team.aliens.remote.annotation

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
