package team.aliens.dms_android.feature.main.image

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.usecase.file.UploadFileUseCase
import team.aliens.domain.usecase.student.EditProfileUseCase
import javax.inject.Inject

@HiltViewModel
internal class EditProfileImageViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
) : BaseMviViewModel<UploadProfileImageIntent, UploadProfileImageState, UploadProfileImageSideEffect>(
    initialState = UploadProfileImageState.initial(),
) {
    override fun processIntent(intent: UploadProfileImageIntent) {
        when (intent) {
            is UploadProfileImageIntent.SelectImage -> reduce(
                newState = stateFlow.value.copy(
                    selectedImageUri = intent.selectedImageUri,
                ),
            )
        }
    }
}

internal sealed class UploadProfileImageIntent : MviIntent {
    class SelectImage(val selectedImageUri: Uri) : UploadProfileImageIntent()
}

internal data class UploadProfileImageState(
    val selectedImageUri: Uri?,
) : MviState {
    companion object {
        fun initial() = UploadProfileImageState(
            selectedImageUri = null,
        )
    }
}

internal sealed class UploadProfileImageSideEffect : MviSideEffect {
    object ProfileEdited : UploadProfileImageSideEffect()
}
