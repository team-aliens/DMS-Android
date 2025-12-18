package team.aliens.dms.android.feature.signup.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.EnterEmailScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationQuestionScreenDestination
import team.aliens.dms.android.feature.destinations.SetIdScreenDestination
import team.aliens.dms.android.feature.destinations.SetProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.TermsScreenDestination

object SignUpNavGraph : NavGraphSpec {
    override val route: String = "sign_up"
    override val startRoute: Route = EnterSchoolVerificationCodeScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            EnterSchoolVerificationCodeScreenDestination,
            EnterSchoolVerificationQuestionScreenDestination,
            EnterEmailScreenDestination,
            SignUpEnterEmailVerificationCodeScreenDestination,
            SetIdScreenDestination,
            SignUpSetPasswordScreenDestination,
            SetProfileImageScreenDestination,
            TermsScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
