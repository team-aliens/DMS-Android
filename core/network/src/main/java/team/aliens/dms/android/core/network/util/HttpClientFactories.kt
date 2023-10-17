package team.aliens.dms.android.core.network.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit(
    clients: List<OkHttpClient>,
    baseUrl: String,
    gsonConverter: Boolean = false,
): Retrofit = Retrofit.Builder().baseUrl(baseUrl).apply {
    if (clients.isNotEmpty()) {
        clients.forEach(this::client)
    }
    if (gsonConverter) {
        addConverterFactory(GsonConverterFactory.create())
    }
}.build()

fun OkHttpClient(
    interceptors: List<Interceptor>,
): OkHttpClient = OkHttpClient().newBuilder().apply {
    if (interceptors.isNotEmpty()) {
        interceptors.forEach(this::addInterceptor)
    }
}.build()
