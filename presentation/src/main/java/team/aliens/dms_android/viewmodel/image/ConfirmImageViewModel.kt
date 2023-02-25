package team.aliens.dms_android.viewmodel.image

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.image.ConfirmImageEvent
import team.aliens.dms_android.feature.image.ConfirmImageState
import team.aliens.domain.usecase.file.RemoteUploadFileUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConfirmImageViewModel @Inject constructor(
    private val remoteUploadFileUseCase: RemoteUploadFileUseCase,
) : BaseViewModel<ConfirmImageState, ConfirmImageEvent>() {

    override val initialState: ConfirmImageState
        get() = ConfirmImageState.getDefaultInstance()

    internal fun uploadImage() {

        val selectedImage = state.value.selectedImage ?: throw IllegalStateException()

        viewModelScope.launch {
            kotlin.runCatching {
                remoteUploadFileUseCase.execute(state.value.selectedImage!!) // non-null checked
            }.onSuccess {
                Log.e("SUCCESS", "uploadFile: $it")
            }.onFailure {
                Log.e("FAILURE", "uploadFile: ${it.printStackTrace()}")
            }
        }
    }

    internal fun setImage(
        profile: File,
    ) {
        state.value.selectedImage = profile
    }

    override fun reduceEvent(
        oldState: ConfirmImageState,
        event: ConfirmImageEvent,
    ) {
    }
}
