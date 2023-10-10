package team.aliens.dms_android.design_system.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import team.aliens.design_system.R
import team.aliens.dms_android.design_system.annotation.DormDeprecated

@DormDeprecated
@Immutable
@JvmInline
value class DormIcon private constructor(
    @DrawableRes val drawableId: Int,
) {

    companion object {

        @Stable
        val Palette = DormIcon(
            drawableId = R.drawable.ic_palette,
        )

        @Stable
        val Applicate = DormIcon(
            drawableId = R.drawable.ic_applicate,
        )

        @Stable
        val BackArrow = DormIcon(
            drawableId = R.drawable.ic_back,
        )

        @Stable
        val Backward = DormIcon(
            drawableId = R.drawable.ic_backward,
        )

        @Stable
        val Forward = DormIcon(
            drawableId = R.drawable.ic_forward,
        )

        @Stable
        val Breakfast = DormIcon(
            drawableId = R.drawable.ic_breakfast,
        )

        @Stable
        val Calender = DormIcon(
            drawableId = R.drawable.ic_calender,
        )

        @Stable
        val Close = DormIcon(
            drawableId = R.drawable.ic_close,
        )

        @Stable
        val Dinner = DormIcon(
            drawableId = R.drawable.ic_dinner,
        )

        @Stable
        val Home = DormIcon(
            drawableId = R.drawable.ic_home,
        )

        @Stable
        val Invisible = DormIcon(
            drawableId = R.drawable.ic_password_visible,
        )

        @Stable
        val Lunch = DormIcon(
            drawableId = R.drawable.ic_lunch,
        )

        @Stable
        val MyPage = DormIcon(
            drawableId = R.drawable.ic_mypage,
        )

        @Stable
        val Notice = DormIcon(
            drawableId = R.drawable.ic_notice,
        )

        @Stable
        val Plus = DormIcon(
            drawableId = R.drawable.ic_plus,
        )

        @Stable
        val Sort = DormIcon(
            drawableId = R.drawable.ic_sort,
        )

        @Stable
        val Visible = DormIcon(
            drawableId = R.drawable.ic_visible,
        )

        @Stable
        val Check = DormIcon(
            drawableId = R.drawable.ic_check,
        )

        @Stable
        val Password_Visible = DormIcon(
            drawableId = R.drawable.ic_password_visible,
        )

        @Stable
        val Password_InVisible = DormIcon(
            drawableId = R.drawable.ic_password_invisible,
        )

        @Stable
        val Information = DormIcon(
            drawableId = R.drawable.ic_information,
        )

        @Stable
        val Warning = DormIcon(
            drawableId = R.drawable.ic_warning,
        )

        @Stable
        val BlueBreakfast = DormIcon(
            drawableId = R.drawable.ic_blue_breakfast,
        )

        @Stable
        val BlueLunch = DormIcon(
            drawableId = R.drawable.ic_blue_lunch,
        )

        @Stable
        val BlueDinner = DormIcon(
            drawableId = R.drawable.ic_blue_dinner,
        )

        @Stable
        val Photo = DormIcon(
            drawableId = R.drawable.ic_photo,
        )

        @Stable
        val Camera = DormIcon(
            drawableId = R.drawable.ic_camera,
        )

        @Stable // todo immutable ㄱㄴ?
        val Bell = DormIcon(
            drawableId = R.drawable.ic_bell,
        )
    }
}