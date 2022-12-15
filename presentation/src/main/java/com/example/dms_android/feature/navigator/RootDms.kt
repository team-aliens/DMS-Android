package com.example.dms_android.feature.navigator

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dms_android.feature.auth.login.LoginScreen

@Composable
fun RootDms() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    NavHost(navController = navController, startDestination = "login") {
        composable(
            route = "login"
        ) {
            LoginScreen(scaffoldState = scaffoldState, navController = navController)
        }
        composable("main") {
            DmsApp(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
    }
}