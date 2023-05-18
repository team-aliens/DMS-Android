package team.aliens.dms_android.feature.register.ui.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.id.SetIdEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.student.CheckIdDuplicationInput
import team.aliens.domain._model.student.ExamineStudentNumberInput
import team.aliens.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.domain.usecase.student.ExamineStudentNumberUseCase
import javax.inject.Inject

@HiltViewModel
class SetIdViewModel @Inject constructor(
    private val examineStudentNumberUseCase: ExamineStudentNumberUseCase,
    private val checkIdDuplicationUseCase: CheckIdDuplicationUseCase,
) : ViewModel() {

    private val _examineGradeEvent = MutableEventFlow<SetIdEvent>()
    val examineGradeEvent = _examineGradeEvent.asEventFlow()

    var name: String = "[ERROR] 이스터에그 : Hello World!"
    var schoolId: UUID = UUID(0, 0)

    fun examineGrade(grade: Int, classRoom: Int, number: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                examineStudentNumberUseCase(
                    examineStudentNumberInput = ExamineStudentNumberInput(
                        grade = grade,
                        classRoom = classRoom,
                        number = number,
                        schoolId = schoolId,
                    ),
                )
            }.onSuccess {
                event(SetIdEvent.ExamineGradeName(it))
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                    else -> event(SetIdEvent.UnknownException)
                }
            }
        }
    }

    fun duplicateId(
        accountId: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                checkIdDuplicationUseCase(
                    checkIdDuplicationInput = CheckIdDuplicationInput(
                        accountId = accountId,
                    ),
                )
            }.onSuccess {
                event(SetIdEvent.DuplicateIdSuccess)
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                    else -> event(SetIdEvent.UnknownException)
                }
            }
        }
    }

    private fun event(event: SetIdEvent) {
        viewModelScope.launch {
            _examineGradeEvent.emit(event)
        }
    }
}