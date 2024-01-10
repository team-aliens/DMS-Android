package team.aliens.dms.android.network.auth.apiservice

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.aliens.dms.android.network.auth.apiservice.AuthApiService
import team.aliens.dms.android.network.auth.model.SignInResponse
import java.io.BufferedReader

@HiltAndroidTest
class AuthApiServiceTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var authApiService: AuthApiService
    private lateinit var context: Context

    @Before
    fun setup() {
/*        this.server = MockWebServer().also(MockWebServer::start)
        this.retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.authApiService = retrofit.create(AuthApiService::class.java)
        this.context = InstrumentationRegistry.getInstrumentation().context*/
    }

    @Test
    fun testtest() {
       /* Gson().fromJson(
            context.readJsonFromAsset("sign_in_response.json"),
            SignInResponse::class.java,
        ).also { println(it) }
*/
        assert(true)
    }

    @After
    fun teardown() {
      //    this.server.shutdown()
    }

    // TODO: read text
    fun Context.readJsonFromAsset(filename: String): String =
        this.assets.open(filename).bufferedReader().use(BufferedReader::readText)
}