package team.aliens.dms.android.core.network.httpclient

import okhttp3.Interceptor

interface GlobalInterceptors {
    val interceptors: List<Interceptor>
}
