package team.aliens.dms.android.feature.main.mypage.navigation

import team.aliens.dms.android.feature._legacy.util.SelectImageType

internal interface MyPageNavigator {

    fun openEditPasswordNav()

    fun openEditProfileImage(selectImageType: SelectImageType = SelectImageType.NONE)

    fun openUnauthorizedNav()
}
