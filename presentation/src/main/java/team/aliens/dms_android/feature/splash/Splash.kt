package team.aliens.dms_android.feature.splash

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.SubTitle1
import team.aliens.dms_android.feature.MainActivity
import team.aliens.presentation.R

@Composable
fun Splash() {
    val viewModel: SplashViewModel = hiltViewModel()
    viewModel.autoLogin()
    Splash(viewModel)
}


@Composable
fun Splash(
    splashViewModel: SplashViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            splashViewModel.eventFlow.collect {
                when (it) {
                    SplashViewModel.Event.AutoLoginSuccess -> {
                        startMainActivity(context, "main")
                    }
                    SplashViewModel.Event.NeedLogin -> startMainActivity(context, "login")
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                modifier = Modifier.size(180.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
            )
            Column {
                Spacer(Modifier.size(142.dp))
                SubTitle1(
                    text = stringResource(id = R.string.Dms),
                    color = DormColor.Gray100,
                )
            }
        }
    }
}

fun startMainActivity(context: Context, route: String) {
    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("route", route)
    }
    context.startActivity(intent)
}