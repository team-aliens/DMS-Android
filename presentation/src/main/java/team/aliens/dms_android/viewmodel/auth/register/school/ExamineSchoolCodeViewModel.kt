package team.aliens.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.schools.RemoteSchoolCodeUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExamineSchoolCodeViewModel @Inject constructor(
    private val remoteSchoolCodeUseCase: RemoteSchoolCodeUseCase,
) : ViewModel() {

    private val _examineSchoolCodeEvent = MutableEventFlow<ExamineSchoolCodeEvent>()
    val examineSchoolCodeEvent = _examineSchoolCodeEvent.asEventFlow()

    var schoolId: UUID = UUID(0, 0)

    fun examineSchoolCode(schoolCode: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                schoolId = remoteSchoolCodeUseCase.execute(schoolCode).schoolId
            }.onSuccess {
                event(ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ExamineSchoolCodeEvent.BadRequestException)
                    is UnauthorizedException -> event(ExamineSchoolCodeEvent.UnAuthorizedException)
                    is TooManyRequestException -> event(ExamineSchoolCodeEvent.TooManyRequestException)
                    is ServerException -> event(ExamineSchoolCodeEvent.InternalServerException)
                    else -> event(ExamineSchoolCodeEvent.UnknownException)
                }
            }
        }
    }

    private fun event(event: ExamineSchoolCodeEvent) {
        viewModelScope.launch {
            _examineSchoolCodeEvent.emit(event)
        }
    }
}