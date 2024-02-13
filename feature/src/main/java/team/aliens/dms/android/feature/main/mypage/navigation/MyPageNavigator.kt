package team.aliens.dms.android.feature.main.mypage.navigation

interface MyPageNavigator {

    fun openEditPasswordNav()

    fun openEditProfileImage(/*selectImageType: SelectImageType = SelectImageType.NONE*/)

    fun openUnauthorizedNav()
}
