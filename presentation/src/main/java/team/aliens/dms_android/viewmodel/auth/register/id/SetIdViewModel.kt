package team.aliens.dms_android.viewmodel.auth.register.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.id.SetIdEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.user.ExamineGradeEntity
import team.aliens.domain.exception.*
import team.aliens.domain.param.ExamineGradeParam
import team.aliens.domain.usecase.students.DuplicateCheckIdUseCase
import team.aliens.domain.usecase.students.ExamineGradeUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SetIdViewModel @Inject constructor(
    private val examineGradeUseCase: ExamineGradeUseCase,
    private val duplicateCheckIdUseCase: DuplicateCheckIdUseCase,
) : ViewModel() {

    private val _examineGradeEvent = MutableEventFlow<SetIdEvent>()
    val examineGradeEvent = _examineGradeEvent.asEventFlow()

    var name: String = "[ERROR] 이스터에그 : Hello World!"
    var schoolId: UUID = UUID(0, 0)

    fun examineGrade(grade: Int, classRoom: Int, number: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                examineGradeUseCase.execute(ExamineGradeParam(grade = grade,
                    classRoom = classRoom,
                    number = number,
                    schoolId = schoolId)).collect {
                    event(SetIdEvent.ExamineGradeName(it.toData()))
                }
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
                duplicateCheckIdUseCase.execute(id)
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