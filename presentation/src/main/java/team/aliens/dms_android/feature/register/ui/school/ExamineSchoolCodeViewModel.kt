package team.aliens.dms_android.feature.register.ui.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain.usecase.school.ExamineSchoolVerificationCodeUseCase
import java.util.TooManyListenersException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ExamineSchoolCodeViewModel @Inject constructor(
    private val examineSchoolVerificationCodeUseCase: ExamineSchoolVerificationCodeUseCase,
) : ViewModel() {

    private val _examineSchoolCodeEvent = MutableEventFlow<ExamineSchoolCodeEvent>()
    val examineSchoolCodeEvent = _examineSchoolCodeEvent.asEventFlow()

    var schoolId: UUID = UUID(0, 0)

    internal fun examineSchoolCode(
        schoolCode: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                schoolId = examineSchoolVerificationCodeUseCase(
                    ExamineSchoolVerificationCodeInput(schoolCode),
                ).schoolId
            }.onSuccess {
                event(ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    private fun event(event: ExamineSchoolCodeEvent) {
        viewModelScope.launch {
            _examineSchoolCodeEvent.emit(event)
        }
    }
}

private fun getEventFromThrowable(
    throwable: Throwable?,
): ExamineSchoolCodeEvent =
    when (throwable) {
        // fixme 추후에 리팩토링 필요
        else -> ExamineSchoolCodeEvent.UnknownException
    }