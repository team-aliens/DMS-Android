package team.aliens.dms.android.core.network.util

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit(
    client: OkHttpClient,
    baseUrl: String,
    gsonConverter: Boolean = true,
    gsonLenient: Boolean = true,
    rxJava2CallAdapter: Boolean = true,
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .apply {
        client(client)
        if (gsonConverter) {
            val builder = GsonBuilder().apply {
                if (gsonLenient) {
                    setLenient()
                }
            }
            addConverterFactory(GsonConverterFactory.create(builder.create()))
        }
        if (rxJava2CallAdapter) {
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }
    }.build()

fun OkHttpClient(
    interceptors: List<Interceptor>,
): OkHttpClient = OkHttpClient().newBuilder()
    .apply {
        interceptors.forEach(this::addInterceptor)
    }.build()
