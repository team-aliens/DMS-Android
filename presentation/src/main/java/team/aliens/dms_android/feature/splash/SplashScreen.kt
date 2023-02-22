package team.aliens.dms_android.feature.splash

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.MainActivity
import team.aliens.presentation.R

// todo 스플래시 스크린을 없애는 방법 검토 필요!@!
@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
) {

    splashViewModel.autoLogin()

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            splashViewModel.eventFlow.collect {
                moveToMainActivity(
                    context,
                    when (it) {
                        SplashViewModel.Event.AutoLoginSuccess -> {
                            "main"
                        }
                        SplashViewModel.Event.NeedLogin -> {
                            "login"
                        }
                    }
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
        )
    }
}

private fun moveToMainActivity(context: Context, route: String) {

    val intent = Intent(
        context,
        MainActivity::class.java,
    )

    intent.putExtra("route", route)

    context.startActivity(intent)
    (context as SplashActivity).finish()
}