package team.aliens.dms.android.network.auth.apiservice

import android.content.Context
import androidx.annotation.RawRes
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.aliens.dms.android.network.auth.model.SignInRequest
import team.aliens.dms.android.network.auth.model.SignInResponse
import team.aliens.dms.android.network.test.R
import java.io.BufferedReader
import java.net.HttpURLConnection

@HiltAndroidTest
class AuthApiServiceTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var authApiService: AuthApiService
    private lateinit var context: Context

    val successResponse by lazy {
        MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(context.readJsonFromRawResources(R.raw.sign_in_response))
        }
    }

    @Before
    fun setup() {
        this.server = MockWebServer()
        this.server.start()
        this.retrofit = Retrofit.Builder().baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
        this.authApiService = retrofit.create(AuthApiService::class.java)
        this.context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun sign_in() {
        this.server.enqueue(successResponse)

        val request = SignInRequest(
            accountId = "student",
            password = "rhqmffls13!",
            deviceToken = "",
        )
        val response = Gson().fromJson(
            context.readJsonFromRawResources(R.raw.sign_in_response),
            SignInResponse::class.java,
        )
        runBlocking {
            val result = authApiService.signIn(request)
            assert(result == response)
        }
    }

    @After
    fun teardown() {
        this.server.shutdown()
    }

    // TODO: read text util
    fun Context.readJsonFromRawResources(@RawRes id: Int): String =
        this.resources.openRawResource(id).bufferedReader().use(BufferedReader::readText)
}
