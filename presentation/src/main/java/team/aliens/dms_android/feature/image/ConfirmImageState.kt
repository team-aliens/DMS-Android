package team.aliens.dms_android.feature.image

import team.aliens.dms_android.base.MviState
import java.io.File

data class ConfirmImageState(
    var selectedImage: File?,
) : MviState {
    companion object {
        fun getDefaultInstance(): ConfirmImageState {
            return ConfirmImageState(
                selectedImage = null,
            )
        }
    }
}