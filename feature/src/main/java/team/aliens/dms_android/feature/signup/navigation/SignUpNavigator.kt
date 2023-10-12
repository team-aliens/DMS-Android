package team.aliens.dms_android.feature.signup.navigation

interface SignUpNavigator {
    fun openEnterSchoolVerificationQuestion()
    fun openEnterEmail(clearStack: Boolean = false)
    fun openSignIn()
    fun openEnterEmailVerificationCode()
    fun openSetId()
}
