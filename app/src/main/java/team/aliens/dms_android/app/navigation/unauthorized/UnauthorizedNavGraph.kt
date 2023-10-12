package team.aliens.dms_android.app.navigation.unauthorized

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.SignInScreenDestination
import team.aliens.dms_android.feature.signup.SignUpNavGraph

object UnauthorizedNavGraph : NavGraphSpec {
    override val route: String = "unauthorized"
    override val startRoute: Route = SignInScreenDestination routedIn this
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            SignUpNavGraph,
        )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            SignInScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
