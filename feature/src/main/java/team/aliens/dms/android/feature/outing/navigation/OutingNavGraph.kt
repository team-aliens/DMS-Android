package team.aliens.dms.android.feature.outing.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.OutingApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.OutingStatusScreenDestination

object OutingNavGraph : NavGraphSpec {
    override val route: String = "outing"
    override val startRoute: Route = OutingStatusScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf<DestinationSpec<*>>(
        OutingStatusScreenDestination,
        OutingApplicationScreenDestination,
    )
        .routedIn(navGraphSpec = this)
        .associateBy { it.route }
}
