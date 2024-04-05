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
import java.util.UUID
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
                schoolId = intent.schoolId,
                schoolName = intent.schoolName
            )

            is FindIdIntent.UpdateName -> this.updateName(name = intent.value)
            is FindIdIntent.UpdateGrade -> this.updateGrade(grade = intent.value)
            is FindIdIntent.UpdateClass -> this.updateClassRoom(classRoom = intent.value)
            is FindIdIntent.UpdateNumber -> this.updateNumber(number = intent.value)
        }
    }
    
    private fun updateSchoolId(schoolId: UUID, schoolName: String): Boolean = reduce(
        newState = stateFlow.value.copy(schoolId = schoolId, schoolName = schoolName)
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
        val uiState = stateFlow.value

        val nameEntered = uiState.name.isNotBlank()
        val gradeEntered = uiState.grade.isNotBlank()
        val classRoomEntered = uiState.classRoom.isNotBlank()
        val numberEntered = uiState.number.isNotBlank()

        return nameEntered && gradeEntered && classRoomEntered && numberEntered
    }

    internal fun onButtonClick() {
        with(stateFlow.value) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    studentRepository.findId(
                        schoolId = schoolId,
                        studentName = name,
                        grade = grade.toInt(),
                        classRoom = classRoom.toInt(),
                        number = number.toInt(),
                    )
                }.onSuccess { email ->
                    reduce(newState = stateFlow.value.copy(email = email))
                    postSideEffect(FindIdSideEffect.Success)
                }.onFailure {
                    postSideEffect(FindIdSideEffect.NotFound)
                }
            }
        }
    }

    private fun fetchSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                schoolRepository.fetchSchools()
            }.onSuccess {
                reduce(newState = stateFlow.value.copy(schoolList = it))
            }.onFailure {
                postSideEffect(FindIdSideEffect.NotFound)
            }
        }
    }
}


internal data class FindIdUiState(
    val schoolList: List<School>,
    val email: String,
    val schoolId: UUID,
    val schoolName: String,
    val name: String,
    val grade: String,
    val classRoom: String,
    val number: String,
    val buttonEnabled: Boolean
) : UiState() {
    companion object {
        fun initial() = FindIdUiState(
            schoolList = emptyList(),
            email = "",
            schoolId = UUID.randomUUID(),
            schoolName = "",
            name = "",
            grade = "",
            classRoom = "",
            number = "",
            buttonEnabled = false,
        )
    }
}

internal sealed class FindIdIntent : Intent() {
    class UpdateSchoolId(val schoolId: UUID, val schoolName: String) : FindIdIntent()
    class UpdateName(val value: String) : FindIdIntent()
    class UpdateGrade(val value: String) : FindIdIntent()
    class UpdateClass(val value: String) : FindIdIntent()
    class UpdateNumber(val value: String) : FindIdIntent()
}

internal sealed class FindIdSideEffect : SideEffect() {
    data object Success : FindIdSideEffect()
    data object NotFound : FindIdSideEffect()
}
