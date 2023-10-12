package team.aliens.dms_android.app.navigation.authorized

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.app.navigation.authorized.main.MainNavGraph
import team.aliens.dms_android.feature.editpassword.EditPasswordNavGraph

object AuthorizedNavGraph : NavGraphSpec {
    override val route: String = "authorized"
    override val startRoute: Route = MainNavGraph
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            MainNavGraph,
            EditPasswordNavGraph,
        )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(

        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
