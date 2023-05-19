package team.aliens.dms_android.feature.auth.changepassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseViewModel1
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.model.auth.CheckIdExistsInput
import team.aliens.domain.model.student.ResetPasswordInput
import team.aliens.domain.model.user.ComparePasswordInput
import team.aliens.domain.model.user.EditPasswordInput
import team.aliens.domain.usecase.auth.CheckIdExistsUseCase
import team.aliens.domain.usecase.student.ResetPasswordUseCase
import team.aliens.domain.usecase.user.ComparePasswordUseCase
import team.aliens.domain.usecase.user.EditPasswordUseCase
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ResetPasswordUseCase,
    private val editPasswordUseCase: EditPasswordUseCase,
    private val comparePasswordUseCase: ComparePasswordUseCase,
    private val checkIdUseCase: CheckIdExistsUseCase,
) : BaseViewModel1<ChangePasswordState, ChangePasswordEvent>() {

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
                        editPasswordUseCase(
                            editPasswordInput = EditPasswordInput(
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
                comparePasswordUseCase(
                    comparePasswordInput = ComparePasswordInput(
                        password = state.value.currentPassword,
                    ),
                )
            }.onSuccess {
                event(Event.ComparePasswordSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun checkId(
        accountId: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                checkIdUseCase(
                    checkIdExistsInput = CheckIdExistsInput(
                        accountId = accountId,
                    ),
                )
            }.onSuccess {
                event(Event.CheckIdSuccess(it.email))
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun resetPassword(
        accountId: String,
        email: String,
        emailVerificationCode: String,
        studentName: String,
        newPassword: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                state.value.run {
                    changePasswordUseCase(
                        resetPasswordInput = ResetPasswordInput(
                            accountId = accountId,
                            studentName = studentName,
                            email = email,
                            emailVerificationCode = emailVerificationCode,
                            newPassword = newPassword,
                        ),
                    )
                }
            }.onSuccess {
                event(Event.ResetPasswordSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
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
            is ChangePasswordEvent.SetCurrentPassword -> {
                setState(state = oldState.copy(currentPassword = event.currentPassword))
            }

            is ChangePasswordEvent.SetRepeatPassword -> {
                setState(state = oldState.copy(repeatPassword = event.repeatPassword))
            }

            is ChangePasswordEvent.SetNewPassword -> {
                setState(state = oldState.copy(newPassword = event.newPassword))
            }

            else -> {}
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _editPasswordEffect.emit(event)
        }
    }

    sealed class Event {
        object EditPasswordSuccess : Event()
        object ComparePasswordSuccess : Event()
        data class CheckIdSuccess(val email: String) : Event()
        object ResetPasswordSuccess : Event()

        object BadRequestException : Event()
        object NotFoundException : Event()
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
        else -> ChangePasswordViewModel.Event.UnknownException
    }
}
