package team.aliens.dms.android.app.navigation.unauthorized

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.FindIdScreenDestination
import team.aliens.dms.android.feature.destinations.SignInScreenDestination
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph

object UnauthorizedNavGraph : NavGraphSpec {
    override val route: String = "unauthorized"
    override val startRoute: Route = SignInScreenDestination routedIn this
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            SignUpNavGraph,
            ResetPasswordNavGraph,
        )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            SignInScreenDestination,
            FindIdScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
