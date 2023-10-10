package team.aliens.dms_android.app.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object DmsNavGraph {

    fun root(autoSignIn: Boolean) = object : NavGraphSpec {
        override val route: String = "root"
        override val startRoute: Route = if (autoSignIn) {
            authorized
        } else {
            unauthorized
        }
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()
        override val nestedNavGraphs: List<NavGraphSpec> =
            listOf(
                authorized,
                unauthorized,
            )
    }

    val authorized = object : NavGraphSpec {
        override val route: String = "authorized"
        override val startRoute: Route = TODO()
        override val destinationsByRoute: Map<String, DestinationSpec<*>> =
            listOf<DestinationSpec<*>>(/* TODO */)
                .routedIn(navGraphSpec = this)
                .associateBy { it.route }
    }

    val unauthorized = object : NavGraphSpec {
        override val route: String = "unauthorized"
        override val startRoute: Route = TODO()
        override val destinationsByRoute: Map<String, DestinationSpec<*>> =
            listOf<DestinationSpec<*>>(/* TODO */)
                .routedIn(navGraphSpec = this)
                .associateBy { it.route }
    }
}
