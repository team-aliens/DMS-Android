package team.aliens.dms_android.feature.auth.findid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.register.event.id.SetIdEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.dms_android.viewmodel.auth.login.SignInViewModel
import team.aliens.domain.exception.*
import team.aliens.domain.exception.NoInternetException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.param.FindIdParam
import team.aliens.domain.usecase.students.FindIdUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val findIdUseCase: FindIdUseCase
) : ViewModel() {
    private val _findIdEvent = MutableEventFlow<FindIdEvent>()
    val findIdEvent = _findIdEvent.asEventFlow()

    var email : String = "이메일"

    internal fun findId(schoolId: UUID, name: String, grade: Int, classRoom: Int, number: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                email = findIdUseCase.execute(
                    FindIdParam(
                        schoolId, name, grade, classRoom, number,
                    )
                ).email
            }.onSuccess {
                event(SuccessFindId)
            }.onFailure {
                when(it) {
                    is NoInternetException -> event(FindIdNoInternetException)
                    is TooManyRequestException -> event(FindIdTooManyRequest)
                    is ServerException -> event(FindIdServerException)
                    is NotFoundException -> event(FindIdNotFound)
                    is BadRequestException -> event(FindIdBadRequest)
                    is UnauthorizedException -> event(FindIdUnauthorized)
                    else -> event(FindIdUnknownException)
                }
            }
        }
    }


    fun event(event : FindIdEvent) {
        viewModelScope.launch {
            _findIdEvent.emit(event)
        }
    }
}