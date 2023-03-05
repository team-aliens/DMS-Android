package team.aliens.dms_android.viewmodel.changepw

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordEvent
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.domain.param.EditPasswordParam
import team.aliens.domain.param.ResetPasswordParam
import team.aliens.domain.usecase.students.RemoteResetPasswordUseCase
import team.aliens.domain.usecase.user.ComparePasswordUseCase
import team.aliens.domain.usecase.user.EditPasswordUseCase
import team.aliens.domain.usecase.user.RemoteCheckIdUseCase
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: RemoteResetPasswordUseCase,
    private val editPasswordUseCase: EditPasswordUseCase,
    private val comparePasswordUseCase: ComparePasswordUseCase,
    private val checkIdUseCase: RemoteCheckIdUseCase,
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>() {

    /*
        디자인에서 처음 본인인증할때 아이디만을 사용해서 "아이디 존재 여부(비밀번호 재설정)"이라는 Api로 이에 해당하는 Email를 받습니다.
        그다음 아이디를 입력 받은 다음에 "이메일 검증이라는 Api를 사용하여 이메일과 아이디를 서버에 보낸뒤 이 값들이 정보와 일치하는지 검사합니다."
        검사에서 가능이 뜨게 된다면 "이메일 인증번호 보내기 APi"를 사용해서 사용자 이메일에 이메일을 발송합니다.
        그리고 이메일 인증번호 확인 Api를 사용하여 인증을 완료하고 Students의 비밀번호 재설정 Api를 사용하여 재설정합니다.
    */

    override val initialState: ChangePasswordState
        get() = ChangePasswordState.getDefaultInstance()

    private val _editPasswordEffect = MutableEventFlow<Event>()
    var editPasswordEffect = _editPasswordEffect.asEventFlow()

    internal fun editPassword() {
        viewModelScope.launch {
            kotlin.runCatching {
                with(state.value) {
                    if (newPassword == repeatPassword) {
                        editPasswordUseCase.execute(
                            data = EditPasswordParam(
                                currentPassword = currentPassword,
                                newPassword = newPassword,
                            )
                        )
                    }
                }
            }.onSuccess {
                event(Event.EditPasswordSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun comparePassword() {
        viewModelScope.launch {
            kotlin.runCatching {
                comparePasswordUseCase.execute(
                    data = state.value.currentPassword,
                )
            }.onSuccess {
                event(Event.ComparePasswordSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun checkId(){
        viewModelScope.launch {
            kotlin.runCatching {
                checkIdUseCase.execute(
                    data = state.value.accountId,
                )
            }.onSuccess {
                event(Event.CheckIdSuccess(it.email))
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun resetPassword(){
        viewModelScope.launch {
            kotlin.runCatching {
                state.value.run {
                    changePasswordUseCase.execute(
                        data = ResetPasswordParam(
                            accountId = accountId,
                            authCode = authCode,
                            email = email,
                            name = name,
                            newPassword = newPassword,
                        )
                    )
                }
            }.onSuccess {
                event(Event.ResetPasswordSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun setId(
        id: String,
    ){
        sendEvent(event = ChangePasswordEvent.SetId(id))
    }

    internal fun setName(
        name: String,
    ){
        sendEvent(event = ChangePasswordEvent.SetName(name))
    }

    internal fun setEmail(
        email: String,
    ){
        sendEvent(event = ChangePasswordEvent.SetEmail(email))
    }

    internal fun setAuthCode(
        authCode: String,
    ){
        sendEvent(event = ChangePasswordEvent.SetAuthCode(authCode))
    }

    internal fun setCurrentPassword(
        currentPassword: String,
    ) {
        sendEvent(event = ChangePasswordEvent.SetCurrentPassword(currentPassword))
    }

    internal fun setRepeatPassword(
        repeatPassword: String,
    ) {
        sendEvent(event = ChangePasswordEvent.SetRepeatPassword(repeatPassword))
    }

    internal fun setNewPassword(
        newPassword: String,
    ) {
        sendEvent(event = ChangePasswordEvent.SetNewPassword(newPassword))
    }

    override fun reduceEvent(oldState: ChangePasswordState, event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.SetId -> {
                setState(state = oldState.copy(accountId = event.id))
            }
            is ChangePasswordEvent.SetName -> {
                setState(state = oldState.copy(name = event.name))
            }
            is ChangePasswordEvent.SetEmail -> {
                setState(state = oldState.copy(email = event.email))
            }
            is ChangePasswordEvent.SetAuthCode -> {
                setState(state = oldState.copy(authCode = event.authCode))
            }
            is ChangePasswordEvent.SetCurrentPassword -> {
                setState(state = oldState.copy(currentPassword = event.currentPassword))
            }
            is ChangePasswordEvent.SetRepeatPassword -> {
                setState(state = oldState.copy(repeatPassword = event.repeatPassword))
            }
            is ChangePasswordEvent.SetNewPassword -> {
                setState(state = oldState.copy(newPassword = event.newPassword))
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _editPasswordEffect.emit(event)
        }
    }

    sealed class Event() {
        object EditPasswordSuccess : Event()
        object ComparePasswordSuccess: Event()
        data class CheckIdSuccess(val email: String): Event()
        object ResetPasswordSuccess: Event()

        object BadRequestException : Event()
        object NotFoundException: Event()
        object UnauthorizedException : Event()
        object ForbiddenException : Event()
        object TooManyRequestException : Event()
        object ServerException : Event()
        object UnknownException : Event()

    }
}

// TODO 추후에 리팩토링 필요
private fun getEventFromThrowable(
    throwable: Throwable?,
): ChangePasswordViewModel.Event {
    return when (throwable) {
        is BadRequestException -> ChangePasswordViewModel.Event.BadRequestException
        is NotFoundException -> ChangePasswordViewModel.Event.NotFoundException
        is UnauthorizedException -> ChangePasswordViewModel.Event.UnauthorizedException
        is ForbiddenException -> ChangePasswordViewModel.Event.ForbiddenException
        is TooManyRequestException -> ChangePasswordViewModel.Event.TooManyRequestException
        is ServerException -> ChangePasswordViewModel.Event.ServerException
        else -> ChangePasswordViewModel.Event.UnknownException
    }
}
