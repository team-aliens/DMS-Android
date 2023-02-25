package team.aliens.dms_android.viewmodel.image

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.image.ConfirmImageEvent
import team.aliens.dms_android.feature.image.ConfirmImageState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.file.RemoteUploadFileUseCase
import team.aliens.domain.usecase.students.RemoteEditProfileImageUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConfirmImageViewModel @Inject constructor(
    private val remoteUploadFileUseCase: RemoteUploadFileUseCase,
    private val remoteEditProfileImageUseCase: RemoteEditProfileImageUseCase,
) : BaseViewModel<ConfirmImageState, ConfirmImageEvent>() {

    override val initialState: ConfirmImageState
        get() = ConfirmImageState.getDefaultInstance()

    private lateinit var profileUrl: String

    private var _confirmImageEvent = MutableEventFlow<Event>()
    internal val confirmImageEvent = _confirmImageEvent.asEventFlow()

    private fun uploadImage() {
        runBlocking {
            kotlin.runCatching {
                remoteUploadFileUseCase.execute(state.value.selectedImage!!) // non-null checked
            }.onSuccess {
                profileUrl = it.fileUrl
            }.onFailure {
                emitEvent(
                    getEventFromThrowable(it),
                )
            }
        }
    }


    internal fun editProfileImage() {
        viewModelScope.launch {
            kotlin.runCatching {

                uploadImage()

                check(this@ConfirmImageViewModel::profileUrl.isInitialized)

                remoteEditProfileImageUseCase.execute(profileUrl)
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
