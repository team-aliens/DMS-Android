package team.aliens.dms_android.viewmodel.auth.register.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.id.SetIdEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.user.ExamineGradeEntity
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.domain.usecase.student.ExamineStudentNumberUseCase
import java.util.UUID
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
                    grade = grade,
                    classRoom = classRoom,
                    number = number,
                    schoolId = schoolId,
                )
            }.onSuccess {
                event(SetIdEvent.ExamineGradeName(it))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(SetIdEvent.ExamineGradeBadRequestException)
                    is NotFoundException -> event(SetIdEvent.ExamineGradeNotFoundException)
                    is ConflictException -> event(SetIdEvent.ExamineGradeConflictException)
                    is TooManyRequestException -> event(SetIdEvent.ExamineGradeTooManyRequestException)
                    is ServerException -> event(SetIdEvent.ExamineGradeInterServerException)
                    else -> event(SetIdEvent.UnknownException)
                }
            }
        }
    }

    fun duplicateId(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                checkIdDuplicationUseCase(id)
            }.onSuccess {
                event(SetIdEvent.DuplicateIdSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(SetIdEvent.DuplicateIdBadRequestException)
                    is ConflictException -> event(SetIdEvent.DuplicateIdConflictException)
                    is TooManyRequestException -> event(SetIdEvent.DuplicateIdTooManyRequestException)
                    is ServerException -> event(SetIdEvent.DuplicateIdInterServerException)
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

    private fun ExamineGradeEntity.toData() = ExamineGradeEntity(name = name)
}