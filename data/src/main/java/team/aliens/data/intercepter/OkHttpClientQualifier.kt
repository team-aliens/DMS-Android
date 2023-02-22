package team.aliens.data.intercepter

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class DefaultOkHttpClient(
    val value: String = "defaultOkHttpClient",
)

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TokenReissueOkHttpClient(
    val value: String = "tokenReissueOkHttpClient",
)
