package team.aliens.dms.android.network.annotation

/**
 * 대상 API Call에 access token이 필요함을 명시하는 애너테이션입니다.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
internal annotation class RequiresAccessToken

/**
 * 대상 API Call에 refresh token이 필요함을 명시하는 애너테이션입니다.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
internal annotation class RequiresRefreshToken
