package team.aliens.dms_android.feature.main.image

import team.aliens.dms_android.base._MviState
import java.io.File

data class ConfirmImageState(
    var selectedImage: File?,
) : _MviState {
    companion object {
        fun getDefaultInstance(): ConfirmImageState {
            return ConfirmImageState(
                selectedImage = null,
            )
        }
    }
}