package team.aliens.dms_android.viewmodel.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.SignUpEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.domain.param.RegisterParam
import team.aliens.domain.usecase.students.RemoteSignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val remoteSignUpUseCase: RemoteSignUpUseCase,
) : ViewModel() {
    private val _signUpEvent = MutableEventFlow<SignUpEvent>()
    val signUpViewEvent = _signUpEvent.asEventFlow()

    fun signUp(registerParam: RegisterParam) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSignUpUseCase.execute(registerParam)
            }.onSuccess {
                event(SignUpEvent.SignUpSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(SignUpEvent.BadRequestException)
                    is UnauthorizedException -> event(SignUpEvent.UnAuthorizedException)
                    is NotFoundException -> event(SignUpEvent.NotFoundException)
                    is TooManyRequestException -> event(SignUpEvent.TooManyRequestsException)
                    is ConflictException -> event(SignUpEvent.ConflictException)
                    is ServerException -> event(SignUpEvent.InternalServerException)
                    else -> event(SignUpEvent.UnKnownException)
                }
            }
        }
    }

    private fun event(event: SignUpEvent) {
        viewModelScope.launch {
            _signUpEvent.emit(event)
        }
    }
}