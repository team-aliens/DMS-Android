package team.aliens.dms.android.feature.signup.navigation

interface SignUpNavigator {
    fun openEnterSchoolVerificationQuestion()
    fun openEnterEmail(clearStack: Boolean = false)
    fun openSignIn()
    fun openSignUpEnterEmailVerificationCode()
    fun openSetId()
    fun openSignUpSetPassword()
    fun openSetProfileImage()
    fun openTerms()
}
