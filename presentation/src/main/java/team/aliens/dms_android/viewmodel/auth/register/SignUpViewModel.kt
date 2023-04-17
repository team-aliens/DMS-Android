package team.aliens.dms_android.viewmodel.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.SignUpEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.student.SignUpInput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.student.SignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    private val _signUpEvent = MutableEventFlow<SignUpEvent>()
    val signUpViewEvent = _signUpEvent.asEventFlow()

    fun signUp(signUpInput: SignUpInput) {
        viewModelScope.launch {
            kotlin.runCatching {
                signUpUseCase(signUpInput)
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
