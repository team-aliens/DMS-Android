package team.aliens.dms.android.feature.volunteers.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.VolunteersScreenDestination

object VolunteersNavGraph : NavGraphSpec {
    override val route: String = "volunteers"
    override val startRoute: Route = VolunteersScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf(
            VolunteersScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
