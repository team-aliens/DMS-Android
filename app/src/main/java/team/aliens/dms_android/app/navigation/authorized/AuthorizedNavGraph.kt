package team.aliens.dms_android.app.navigation.authorized

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object AuthorizedNavGraph : NavGraphSpec {
    override val route: String = "authorized"
    override val startRoute: Route = TODO()
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(/* TODO */)
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
