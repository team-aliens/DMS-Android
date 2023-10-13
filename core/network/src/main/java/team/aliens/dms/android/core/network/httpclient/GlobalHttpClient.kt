package team.aliens.dms.android.core.network.httpclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.network.di.DefaultHttpClient
import javax.inject.Inject

internal class GlobalHttpClient @Inject constructor(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    @DefaultHttpClient baseHttpClient: OkHttpClient,
) : OkHttpClient() {
    init {
        this.apply {
            /* config http client */
        }
    }
}
