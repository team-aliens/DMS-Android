package team.aliens.dms.android.network._legacy.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 참고 자료 : https://kyucumber.github.io/posts/book/effective-kotlin/effective-kotlin-item-33
// pr 확인하시고 주석 지워주세요!
/**
 * 기존 Retrofit의 Builder 객체를 대체할 수 있는 팩토리 메서드입니다.
 */
fun Retrofit(
    vararg clients: OkHttpClient,
    baseUrl: String,
    gsonConverter: Boolean = false,
): Retrofit {

    var retrofit = Retrofit.Builder().baseUrl(
        baseUrl,
    )

    // clients가 존재하는 경우, retrofit에 client를 추가합니다
    if (clients.isNotEmpty())
        for (client in clients) {
            retrofit = retrofit.client(client)
        }

    // gsonConverterFactory 옵션이 활성화되어있는 경우, GsonConverterFactory를 추가합니다
    if (gsonConverter)
        retrofit = retrofit.addConverterFactory(
            GsonConverterFactory.create(),
        )

    return retrofit.build()
}

/**
 * 기존 OkHttpClient의 빌더 패턴을 대체할 수 있는 팩토리 메서드입니다.
 */
fun OkHttpClient(
    vararg interceptors: Interceptor,
): OkHttpClient {

    var client = OkHttpClient().newBuilder()

    // interceptors가 존재하는 경우, client에 interceptor를 추가합니다
    if (interceptors.isNotEmpty())
        for (interceptor in interceptors) {
            client = client.addInterceptor(interceptor)
        }

    return client.build()
}
