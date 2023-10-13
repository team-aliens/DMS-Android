package team.aliens.dms.android.feature.findid.navigator

interface FindIdNavigator {
    fun openSignIn()

    fun popBackStack()
    fun navigateUp()
}
