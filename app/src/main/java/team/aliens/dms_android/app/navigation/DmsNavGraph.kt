package team.aliens.dms_android.app.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object DmsNavGraph {
    fun root(autoSignIn: Boolean): NavGraphSpec =
        object : NavGraphSpec {
            override val route: String = "root"
            override val startRoute: Route = if (autoSignIn) {
                AuthorizedNavGraph
            } else {
                UnauthorizedNavGraph
            }

            override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()
            override val nestedNavGraphs: List<NavGraphSpec> =
                listOf(
                    AuthorizedNavGraph,
                    UnauthorizedNavGraph,
                )
        }
}
