package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.feature.signup.model.SignUpData
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class EnterStudentNumberViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<EnterStudentNumberState, EnterStudentNumberSideEffect>(EnterStudentNumberState()) {

    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
    }

    internal fun setGrade(grade: String) {
        setState { it.copy(grade = grade) }
        updateButtonEnabled()
    }

    internal fun setClassRoom(classroom: String) {
        setState { it.copy(classroom = classroom) }
        updateButtonEnabled()
    }

    internal fun setNumber(number: String) {
        setState { it.copy(number = number) }
        updateButtonEnabled()
    }

    private fun updateButtonEnabled() = setState {
        it.copy(buttonEnabled = it.grade.isNotBlank() && it.classroom.isNotBlank() && it.number.isNotBlank())
    }

    internal fun onNextClick() {
        val grade = uiState.value.grade.toIntOrNull() ?: return
        val classroom = uiState.value.classroom.toIntOrNull() ?: return
        val number = uiState.value.number.toIntOrNull() ?: return
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            studentRepository.examineStudentNumber(
                schoolId = UUID.fromString(currentSignUpData.schoolId),
                grade = grade,
                classroom = classroom,
                number = number,
            ).onSuccess {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(
                    EnterStudentNumberSideEffect.MoveToSetId(
                        signUpData = currentSignUpData.copy(
                            grade = grade,
                            classRoom = classroom,
                            number = number,
                        )
                    )
                )
            }.onFailure { exception ->
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                when (exception) {
                    is ConflictException -> sendEffect(EnterStudentNumberSideEffect.ShowConflictSnackBar)
                    else -> sendEffect(EnterStudentNumberSideEffect.ShowErrorSnackBar)
                }
            }
        }
    }
}

internal data class EnterStudentNumberState(
    val grade: String = "",
    val classroom: String = "",
    val number: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface EnterStudentNumberSideEffect {
    data class MoveToSetId(val signUpData: SignUpData) : EnterStudentNumberSideEffect
    data object ShowConflictSnackBar : EnterStudentNumberSideEffect
    data object ShowErrorSnackBar : EnterStudentNumberSideEffect
}
