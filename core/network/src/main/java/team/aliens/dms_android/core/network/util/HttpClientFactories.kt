package team.aliens.dms_android.core.network.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit(
    vararg clients: OkHttpClient,
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
    vararg interceptors: Interceptor,
): OkHttpClient = OkHttpClient().newBuilder().apply {
    if (interceptors.isNotEmpty()) {
        interceptors.forEach(this::addInterceptor)
    }
}.build()
