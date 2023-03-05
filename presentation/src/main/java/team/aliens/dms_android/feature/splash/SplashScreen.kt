package team.aliens.dms_android.feature.splash

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
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
import team.aliens.design_system.color.DormColor
import team.aliens.dms_android.feature.MainActivity
import team.aliens.dms_android.viewmodel.root.RootViewModel
import team.aliens.presentation.R

// todo 스플래시 스크린을 없애는 방법 검토 필요!@!
@Composable
fun SplashScreen(
    rootViewModel: RootViewModel = hiltViewModel(),
) {

    rootViewModel.autoLogin()

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            rootViewModel.eventFlow.collect {
                moveToMainActivity(context, when (it) {
                    RootViewModel.Event.AutoLoginSuccess -> {
                        "main"
                    }
                    RootViewModel.Event.NeedLogin -> {
                        "login"
                    }
                })
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Box(
            modifier = Modifier
                .background(
                    // fixme refactor
                    if (isSystemInDarkTheme()) DormColor.Gray900 else DormColor.Gray200,
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(280.dp),
                painter = painterResource(
                    id = R.drawable.ic_logo_image,
                ),
                contentDescription = null,
            )
        }
    }
}

private fun moveToMainActivity(
    context: Context,
    route: String,
) {

    val intent = Intent(
        context,
        MainActivity::class.java,
    )

    intent.putExtra("route", route)

    context.startActivity(intent)
    (context as SplashActivity).finish()
}