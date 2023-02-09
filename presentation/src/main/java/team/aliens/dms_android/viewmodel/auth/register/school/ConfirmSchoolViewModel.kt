package team.aliens.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.ConfirmSchoolEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity
import team.aliens.domain.exception.*
import team.aliens.domain.param.SchoolAnswerParam
import team.aliens.domain.usecase.schools.RemoteSchoolAnswerUseCase
import team.aliens.domain.usecase.schools.RemoteSchoolQuestionUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val remoteSchoolAnswerUseCase: RemoteSchoolAnswerUseCase,
    private val remoteSchoolQuestionUseCase: RemoteSchoolQuestionUseCase,
) : ViewModel() {

    private val _confirmSchoolEvent = MutableEventFlow<ConfirmSchoolEvent>()
    val confirmSchoolEvent = _confirmSchoolEvent.asEventFlow()

    var schoolId: UUID = UUID(0, 0)

    fun compareSchoolAnswer(answer: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolAnswerUseCase.execute(SchoolAnswerParam(schoolId = schoolId,
                    answer = answer))
            }.onSuccess {
                event(ConfirmSchoolEvent.CompareSchoolAnswerSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.CompareSchoolBadRequestException)
                    is UnauthorizedException -> event(ConfirmSchoolEvent.CompareSchoolUnauthorizedException)
                    is NotFoundException -> event(ConfirmSchoolEvent.CompareSchoolNotFoundException)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.CompareSchoolTooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.CompareSchoolInternalServerException)
                    else -> event(ConfirmSchoolEvent.UnknownException)
                }
            }
        }
    }


    fun schoolQuestion(schoolId: UUID) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolQuestionUseCase.execute(schoolId).collect {
                    event(ConfirmSchoolEvent.FetchSchoolQuestion(it.toData()))
                }
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.SchoolQuestionBadRequestException)
                    is NotFoundException -> event(ConfirmSchoolEvent.SchoolQuestionNotFoundException)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.SchoolQuestionTooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.SchoolQuestionInternalServerException)
                    else -> event(ConfirmSchoolEvent.UnknownException)
                }
            }
        }
    }

    private fun event(event: ConfirmSchoolEvent) {
        viewModelScope.launch {
            _confirmSchoolEvent.emit(event)
        }
    }

    private fun SchoolConfirmQuestionEntity.toData() =
        SchoolConfirmQuestionEntity(question = question)
}