package team.aliens.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.CompareSchoolAnswerSuccess
import team.aliens.dms_android.feature.register.event.school.ConfirmSchoolEvent
import team.aliens.dms_android.feature.register.event.school.FetchSchoolQuestion
import team.aliens.dms_android.feature.register.event.school.MissMatchCompareSchool
import team.aliens.dms_android.feature.register.event.school.NotFoundCompareSchool
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.school.ExamineSchoolVerificationQuestionUseCase
import team.aliens.domain.usecase.school.FetchSchoolQuestionUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val examineSchoolVerificationQuestionUseCase: ExamineSchoolVerificationQuestionUseCase,
    private val fetchSchoolQuestionUseCase: FetchSchoolQuestionUseCase,
) : ViewModel() {

    private val _confirmSchoolEvent = MutableEventFlow<ConfirmSchoolEvent>()
    val confirmSchoolEvent = _confirmSchoolEvent.asEventFlow()

    fun compareSchoolAnswer(
        answer: String,
        schoolId: UUID,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                examineSchoolVerificationQuestionUseCase(
                    schoolId = schoolId,
                    answer = answer,
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


    fun schoolQuestion(
        schoolId: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSchoolQuestionUseCase(schoolId)
            }.onSuccess {
                event(FetchSchoolQuestion(it))
            }
        }
    }

    private fun event(event: ConfirmSchoolEvent) {
        viewModelScope.launch {
            _confirmSchoolEvent.emit(event)
        }
    }
}