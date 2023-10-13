package team.aliens.dms.android.feature.main.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms_android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms_android.feature.destinations.HomeScreenDestination
import team.aliens.dms_android.feature.destinations.MyPageScreenDestination

object MainNavGraph : NavGraphSpec {
    override val route: String = "main"
    override val startRoute: Route = HomeScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            ApplicationScreenDestination,
            AnnouncementListScreenDestination,
            MyPageScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
