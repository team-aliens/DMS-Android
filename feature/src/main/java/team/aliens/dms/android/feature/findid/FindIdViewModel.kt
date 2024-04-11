package team.aliens.dms.android.feature.findid


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.school.model.School
import team.aliens.dms.android.data.school.repository.SchoolRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
internal class FindIdViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val schoolRepository: SchoolRepository,
) : BaseMviViewModel<FindIdUiState, FindIdIntent, FindIdSideEffect>(
    initialState = FindIdUiState.initial(),
) {

    init {
        fetchSchools()
    }

    override fun processIntent(intent: FindIdIntent) {
        when (intent) {
            is FindIdIntent.UpdateSchoolId -> this.updateSchoolId(
                selectedSchool = intent.value
            )

            is FindIdIntent.UpdateName -> this.updateName(name = intent.value)
            is FindIdIntent.UpdateGrade -> this.updateGrade(grade = intent.value)
            is FindIdIntent.UpdateClass -> this.updateClassRoom(classRoom = intent.value)
            is FindIdIntent.UpdateNumber -> this.updateNumber(number = intent.value)

            is FindIdIntent.FindId -> onButtonClick()
        }
    }

    private fun updateSchoolId(selectedSchool: School): Boolean = reduce(
        newState = stateFlow.value.copy(selectedSchool = selectedSchool)
    ).also { updateButtonEnable(checkInAvailable()) }

    private fun updateName(name: String): Boolean = reduce(
        newState = stateFlow.value.copy(name = name)
    ).also { updateButtonEnable(checkInAvailable()) }

    private fun updateGrade(grade: String): Boolean = reduce(
        newState = stateFlow.value.copy(grade = grade)
    ).also { updateButtonEnable(checkInAvailable()) }

    private fun updateClassRoom(classRoom: String): Boolean = reduce(
        newState = stateFlow.value.copy(classRoom = classRoom)
    ).also { updateButtonEnable(checkInAvailable()) }

    private fun updateNumber(number: String): Boolean = reduce(
        newState = stateFlow.value.copy(number = number)
    ).also { updateButtonEnable(checkInAvailable()) }

    private fun updateButtonEnable(buttonEnabled: Boolean): Boolean =
        reduce(newState = stateFlow.value.copy(buttonEnabled = buttonEnabled))

    private fun checkInAvailable(): Boolean {
        val capturedState = stateFlow.value

        val nameEntered = capturedState.name.isNotBlank()
        val gradeEntered = capturedState.grade.isNotBlank()
        val classRoomEntered = capturedState.classRoom.isNotBlank()
        val numberEntered = capturedState.number.isNotBlank()

        return nameEntered && gradeEntered && classRoomEntered && numberEntered
    }

    private fun onButtonClick() = run {
        with(stateFlow.value) {
            if (selectedSchool == null) {
                postSideEffect(FindIdSideEffect.SchoolNotSelected)
                return@run
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    runCatching {
                        studentRepository.findId(
                            schoolId = selectedSchool.id,
                            studentName = selectedSchool.name,
                            grade = grade.toInt(),
                            classRoom = classRoom.toInt(),
                            number = number.toInt(),
                        )
                    }.onSuccess { email ->
                        reduce(newState = stateFlow.value.copy(email = email))
                        postSideEffect(FindIdSideEffect.UserFound)
                    }.onFailure {
                        postSideEffect(FindIdSideEffect.UserNotFound)
                    }
                }
            }
        }
    }

    private fun fetchSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                schoolRepository.fetchSchools()
            }.onSuccess { fetchSchools ->
                reduce(newState = stateFlow.value.copy(schoolList = fetchSchools))
            }.onFailure {
                postSideEffect(FindIdSideEffect.SchoolNotFound)
            }
        }
    }
}


internal data class FindIdUiState(
    val schoolList: List<School>?,
    val email: String,
    val selectedSchool: School?,
    val name: String,
    val grade: String,
    val classRoom: String,
    val number: String,
    val buttonEnabled: Boolean
) : UiState() {
    companion object {
        fun initial() = FindIdUiState(
            schoolList = null,
            email = "",
            selectedSchool = null,
            name = "",
            grade = "",
            classRoom = "",
            number = "",
            buttonEnabled = false,
        )
    }
}

internal sealed class FindIdIntent : Intent() {
    class UpdateSchoolId(val value: School) : FindIdIntent()
    class UpdateName(val value: String) : FindIdIntent()
    class UpdateGrade(val value: String) : FindIdIntent()
    class UpdateClass(val value: String) : FindIdIntent()
    class UpdateNumber(val value: String) : FindIdIntent()
    data object FindId : FindIdIntent()
}

internal sealed class FindIdSideEffect : SideEffect() {
    data object UserFound : FindIdSideEffect()
    data object UserNotFound : FindIdSideEffect()
    data object SchoolNotFound : FindIdSideEffect()
    data object SchoolNotSelected : FindIdSideEffect()
}
