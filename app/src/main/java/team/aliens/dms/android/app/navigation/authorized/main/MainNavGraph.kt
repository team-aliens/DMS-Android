package team.aliens.dms.android.app.navigation.authorized.main

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms.android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.HomeScreenDestination
import team.aliens.dms.android.feature.destinations.MyPageScreenDestination

object MainNavGraph : NavGraphSpec {
    override val route: String = "main"
    override val startRoute: Route = HomeScreenDestination routedIn AuthorizedNavGraph
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            HomeScreenDestination routedIn this,
            ApplicationScreenDestination routedIn this,
            AnnouncementListScreenDestination routedIn this,
            MyPageScreenDestination routedIn this,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
