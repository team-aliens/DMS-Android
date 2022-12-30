package com.example.dms_android.feature.splash

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.design_system.color.DormColor
import com.example.design_system.typography.SubTitle1
import com.example.dms_android.R
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.navigator.NavigationRoute
import kotlinx.coroutines.launch

@Composable
fun Splash() {
    val viewModel: SplashViewModel = hiltViewModel()
    viewModel.autoLogin()
    Splash(viewModel)
}


@Composable
fun Splash(
    splashViewModel: SplashViewModel
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier.size(180.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null
            )
            Column {
                Spacer(Modifier.size(142.dp))
                SubTitle1(
                    text = "DMS",
                    color = DormColor.Gray100
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