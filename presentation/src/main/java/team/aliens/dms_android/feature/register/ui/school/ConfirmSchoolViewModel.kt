package team.aliens.dms_android.feature.register.ui.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.CompareSchoolAnswerSuccess
import team.aliens.dms_android.feature.register.event.school.ConfirmSchoolEvent
import team.aliens.dms_android.feature.register.event.school.FetchSchoolQuestion
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain.usecase.school.ExamineSchoolVerificationQuestionUseCase
import team.aliens.domain.usecase.school.FetchSchoolVerificationQuestionUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val examineSchoolVerificationQuestionUseCase: ExamineSchoolVerificationQuestionUseCase,
    private val fetchSchoolVerificationQuestionUseCase: FetchSchoolVerificationQuestionUseCase,
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
                    examineSchoolVerificationQuestionInput = ExamineSchoolVerificationQuestionInput(
                        schoolId = schoolId,
                        answer = answer,
                    ),
                )
            }.onSuccess {
                event(CompareSchoolAnswerSuccess)
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                }
            }
        }
    }


    fun schoolQuestion(
        schoolId: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSchoolVerificationQuestionUseCase(
                    fetchSchoolVerificationQuestionInput = FetchSchoolVerificationQuestionInput(
                        schoolId = schoolId,
                    ),
                )
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