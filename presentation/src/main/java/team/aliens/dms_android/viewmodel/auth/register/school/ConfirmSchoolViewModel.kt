package team.aliens.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.*
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

    fun compareSchoolAnswer(
        answer: String,
        schoolId: UUID,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolAnswerUseCase.execute(
                    SchoolAnswerParam(
                        schoolId = schoolId,
                        answer = answer
                    )
                )
            }.onSuccess {
                event(CompareSchoolAnswerSuccess)
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> event(MissMatchCompareSchool)
                    is NotFoundException -> event(NotFoundCompareSchool)
                }
            }
        }
    }


    fun schoolQuestion(schoolId: UUID) {
        viewModelScope.launch {
            remoteSchoolQuestionUseCase.execute(schoolId).collect {
                event(FetchSchoolQuestion(it.toData()))
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