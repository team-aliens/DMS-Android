package team.aliens.dms.android.feature.resetpassword.navigation

interface ResetPasswordNavigator {
    fun openResetPasswordEnterEmailVerificationCode()
    fun openResetPasswordSetPassword()
    fun openSignIn()

    fun popBackStack()
}
