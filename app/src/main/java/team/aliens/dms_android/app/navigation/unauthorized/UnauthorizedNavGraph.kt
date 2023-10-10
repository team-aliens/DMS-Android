package team.aliens.dms_android.app.navigation.unauthorized

import com.ramcosta.composedestinations.annotation.NavGraph

object UnauthorizedDestinations {
    const val route = "unauthorized"
}

@NavGraph(route = UnauthorizedDestinations.route)
annotation class UnauthorizedNavGraph
