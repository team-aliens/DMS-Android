package team.aliens.data.intercepter

import javax.inject.Qualifier

/**
 * @author junsuPark
 * Annotation, should inject global OkHttpClient, calling common APIs.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultOkHttpClient(
    val value: String = "defaultOkHttpClient",
)

/**
 * @author junsuPark
 * Annotation, should inject OkHttpClient only for reissuing tokens, calling unique reissuing API.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueOkHttpClient(
    val value: String = "tokenReissueOkHttpClient",
)
