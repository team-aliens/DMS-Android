package team.aliens.dms.android.core.network.util

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

fun Retrofit(
    client: OkHttpClient,
    baseUrl: String,
    nullConverter: Boolean = true,
    gsonConverter: Boolean = true,
    gsonLenient: Boolean = true,
    rxJava3CallAdapter: Boolean = true,
): Retrofit = Retrofit.Builder().baseUrl(baseUrl).apply {
    client(client)
    if (nullConverter) {
        addConverterFactory(nullOnEmptyConverterFactory)
    }
    if (gsonConverter) {
        val builder = GsonBuilder().apply {
            if (gsonLenient) {
                setLenient()
            }
        }
        addConverterFactory(GsonConverterFactory.create(builder.create()))
    }
    if (rxJava3CallAdapter) {
        addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    }
}.build()

fun OkHttpClient(
    interceptors: List<Interceptor>,
): OkHttpClient = OkHttpClient().newBuilder().apply {
    interceptors.forEach(this::addInterceptor)
}.build()

private val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter =
            retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
            nextResponseBodyConverter.convert(value)
        } else {
            null
        }
    }
}
