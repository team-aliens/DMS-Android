package team.aliens.dms_android.viewmodel.image

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.image.ConfirmImageEvent
import team.aliens.dms_android.feature.image.ConfirmImageState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.file.UploadFileInput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.NoInternetException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.file.UploadFileUseCase
import team.aliens.domain.usecase.student.EditProfileUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConfirmImageViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
) : BaseViewModel<ConfirmImageState, ConfirmImageEvent>() {

    override val initialState: ConfirmImageState
        get() = ConfirmImageState.getDefaultInstance()

    internal lateinit var profileImageUrl: String

    private var _confirmImageEvent = MutableEventFlow<Event>()
    internal val confirmImageEvent = _confirmImageEvent.asEventFlow()

    private var _confirmImageButtonState = MutableStateFlow(true)
    internal val confirmImageButtonState = _confirmImageButtonState.asStateFlow()

    internal fun uploadImage() {
        runBlocking {
            kotlin.runCatching {
                uploadFileUseCase(
                    uploadFileInput = UploadFileInput(
                        state.value.selectedImage!!,
                    ), // non-null checked
                )
            }.onSuccess {
                profileImageUrl = it.fileUrl
            }.onFailure {
                emitEvent(
                    getEventFromThrowable(it),
                )
            }
        }
    }


    internal fun editProfileImage() {
        setConfirmButtonState(false)
        viewModelScope.launch {
            kotlin.runCatching {

                uploadImage()

                check(this@ConfirmImageViewModel::profileImageUrl.isInitialized)

                editProfileUseCase(
                    EditProfileInput(
                        profileImageUrl = profileImageUrl,
                    ),
                )
            }.onSuccess {
                emitEvent(
                    Event.ProfileEdited,
                )
            }.onFailure {
                emitEvent(
                    getEventFromThrowable(it),
                )
            }
        }
    }


    private fun emitEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _confirmImageEvent.emit(
                event,
            )
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

    internal fun setConfirmButtonState(
        confirmButtonState: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _confirmImageButtonState.emit(confirmButtonState)
        }
    }

    sealed class Event {
        object ProfileEdited : Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}

private fun getEventFromThrowable(
    throwable: Throwable,
): ConfirmImageViewModel.Event {
    return when (throwable) {
        is BadRequestException -> ConfirmImageViewModel.Event.BadRequestException
        is UnauthorizedException -> ConfirmImageViewModel.Event.UnAuthorizedTokenException
        is NoInternetException -> ConfirmImageViewModel.Event.CannotConnectException
        is TooManyRequestException -> ConfirmImageViewModel.Event.TooManyRequestException
        is ServerException -> ConfirmImageViewModel.Event.InternalServerException
        else -> ConfirmImageViewModel.Event.UnknownException
    }
}
