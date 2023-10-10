package team.aliens.dms_android.app.navigation.authorized

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

// TODO: consider making abstraction
object AuthorizedDestinations {
    const val route = "authorized"
}

@RootNavGraph(start = true)
@NavGraph(route = AuthorizedDestinations.route)
annotation class AuthorizedNavGraph
