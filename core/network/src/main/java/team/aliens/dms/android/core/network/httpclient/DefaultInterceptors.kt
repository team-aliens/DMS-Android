package team.aliens.dms.android.core.network.httpclient

import okhttp3.Interceptor

interface DefaultInterceptors {
    val interceptors: List<Interceptor>
}
