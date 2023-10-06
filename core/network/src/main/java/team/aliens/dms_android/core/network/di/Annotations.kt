// TODO: move file to JWT module

package team.aliens.dms_android.core.network.di

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class RequiresAccessToken

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class RequiresRefreshToken
