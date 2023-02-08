package team.aliens.design_system.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.design_system.R

@Immutable
@JvmInline
value class DormIcon private constructor(
    @DrawableRes val drawableId: Int,
) {

    companion object {
        @Stable
        val Applicate = DormIcon(
            drawableId = R.drawable.ic_applicate,
        )

        @Stable
        val BackArrow = DormIcon(
            drawableId = R.drawable.ic_backarrow,
        )

        @Stable
        val Backward = DormIcon(
            drawableId = R.drawable.ic_backward,
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
            drawableId = R.drawable.ic_invisible,
        )

        @Stable
        val Launch = DormIcon(
            drawableId = R.drawable.ic_launch,
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
        val Password_Visible = DormIcon(drawableId = R.drawable.ic_password_visible)

        @Stable
        val Password_InVisible = DormIcon(drawableId = R.drawable.ic_invisible)

        @Stable
        val Information = DormIcon(
            drawableId = R.drawable.ic_information_toast,
        )

        @Stable
        val Error = DormIcon(
            drawableId = R.drawable.ic_error_toast,
        )

        @Stable
        val Success = DormIcon(drawableId = R.drawable.ic_error_toast)
    }
}