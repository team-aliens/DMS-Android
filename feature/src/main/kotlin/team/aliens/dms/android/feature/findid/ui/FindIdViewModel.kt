package team.aliens.dms.android.feature.findid

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSource
import javax.inject.Inject

@HiltViewModel
internal class FindIdViewModel @Inject constructor(
    private val networkSchoolDataSource: NetworkSchoolDataSource,
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<FindIdState, FindIdSideEffect>(FindIdState()) {

    internal fun setName(name: String) {
        setState { it.copy(name = name) }
        setButtonEnabled()
    }

    internal fun setGrade(grade: String) {
        setState { it.copy(grade = grade) }
        setButtonEnabled()
    }

    internal fun setClassRoom(classRoom: String) {
        setState { it.copy(classRoom = classRoom) }
        setButtonEnabled()
    }

    internal fun setNumber(number: String) {
        setState { it.copy(number = number) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() {
        setState {
            with(uiState.value) {
                it.copy(buttonEnabled = name.isNotEmpty() && grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty())
            }
        }
    }

    internal fun findId() = viewModelScope.launch {
        val schoolId = runCatching { networkSchoolDataSource.fetchSchools() }
            .getOrNull()?.schools?.firstOrNull()?.id ?: run {
            sendEffect(FindIdSideEffect.ShowServerErrorSnackBar)
            return@launch
        }
        val grade = uiState.value.grade.toIntOrNull() ?: run {
            sendEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
            return@launch
        }
        val classRoom = uiState.value.classRoom.toIntOrNull() ?: run {
            sendEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
            return@launch
        }
        val number = uiState.value.number.toIntOrNull() ?: run {
            sendEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
            return@launch
        }

        setState { it.copy(isLoading = true) }
        studentRepository.findId(
            schoolId = schoolId,
            studentName = uiState.value.name,
            grade = grade,
            classRoom = classRoom,
            number = number,
        ).onSuccess { hashedEmail ->
            setState { it.copy(hashedEmail = hashedEmail, isShowIdDialog = true) }
        }.onFailure {
            sendEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
        }
        setState { it.copy(isLoading = false) }
    }

    internal fun navigateBack() {
        sendEffect(FindIdSideEffect.NavigateBack)
    }

    internal fun hideDialog() {
        setState { it.copy(isShowIdDialog = false) }
    }
}

internal data class FindIdState(
    val name: String = "",
    val grade: String = "",
    val classRoom: String = "",
    val number: String = "",
    val hashedEmail: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isShowIdDialog: Boolean = false,
)

internal sealed interface FindIdSideEffect {
    data object NavigateBack : FindIdSideEffect
    data object ShowNumberErrorSnackBar : FindIdSideEffect
    data object ShowServerErrorSnackBar : FindIdSideEffect
}
