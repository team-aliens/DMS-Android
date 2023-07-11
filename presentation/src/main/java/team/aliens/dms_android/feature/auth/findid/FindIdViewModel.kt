package team.aliens.dms_android.feature.auth.findid

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.exception.AuthException
import team.aliens.domain.exception.RemoteException
import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.domain.model.student.FindIdInput
import team.aliens.domain.usecase.school.FetchSchoolsUseCase
import team.aliens.domain.usecase.student.FindIdUseCase
import javax.inject.Inject

@HiltViewModel
internal class FindIdViewModel @Inject constructor(
    private val findIdUseCase: FindIdUseCase,
    private val fetchSchoolsUseCase: FetchSchoolsUseCase,
) : BaseMviViewModel<FindIdIntent, FindIdState, FindIdSideEffect>(
    initialState = FindIdState.initial(),
) {
    init {
        fetchSchools()
    }

    private val nameEntered: Boolean
        get() = stateFlow.value.name.isNotBlank()
    private val gradeEntered: Boolean
        get() = stateFlow.value.grade.isNotBlank()
    private val classRoomEntered: Boolean
        get() = stateFlow.value.classRoom.isNotBlank()
    private val numberEntered: Boolean
        get() = stateFlow.value.number.isNotBlank()


    override fun processIntent(intent: FindIdIntent) {
        when (intent) {
            is FindIdIntent.FindId -> this.findId()
            is FindIdIntent.FetchSchools -> this.fetchSchools()
            is FindIdIntent.UpdateGrade -> this.setGrade(newGrade = intent.newGrade)
            is FindIdIntent.UpdateClassRoom -> this.setClassRoom(newClassRoom = intent.newClassRoom)
            is FindIdIntent.UpdateNumber -> this.setNumber(newNumber = intent.newNumber)
            is FindIdIntent.UpdateName -> this.setName(newName = intent.newName)
        }
    }

    private fun findId() {
        setFindIdButtonState(false)

        viewModelScope.launch {
            kotlin.runCatching {
                val currentUserInformation = this@FindIdViewModel.stateFlow.value
                findIdUseCase(
                    FindIdInput(
                        schoolId = currentUserInformation.schools?.id!!,
                        studentName = currentUserInformation.name,
                        grade = currentUserInformation.grade.toInt(),
                        classRoom = currentUserInformation.classRoom.toInt(),
                        number = currentUserInformation.number.toInt(),
                    ),
                )
            }.onSuccess {
                postSideEffect(
                    sideEffect = FindIdSideEffect.FindIdSuccess(email = it.email)
                )
            }.onFailure {
                when (it) {
                    RemoteException.BadRequest -> postSideEffect(FindIdSideEffect.BadRequest)
                    AuthException.UserNotFound -> postSideEffect(FindIdSideEffect.NotFound)
                }
            }
        }
    }

    private fun fetchSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSchoolsUseCase()
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    private fun setGrade(newGrade: String) {
        reduce(
            newState = currentState.copy(
                gradeError = false,
                grade = newGrade,
            ),
        )

        setFindIdButtonState()
    }

    private fun setClassRoom(newClassRoom: String) {
        reduce(
            newState = currentState.copy(
                classRoomError = false,
                classRoom = newClassRoom,
            ),
        )

        setFindIdButtonState()
    }

    private fun setNumber(newNumber: String) {
        reduce(
            newState = currentState.copy(
                numberError = false,
                number = newNumber,
            ),
        )

        setFindIdButtonState()
    }

    private fun setName(newName: String) {
        reduce(
            newState = currentState.copy(
                nameError = false,
                name = newName,
            ),
        )

        setFindIdButtonState()
    }

    private fun setFindIdButtonState(
        enabled: Boolean = nameEntered && gradeEntered && classRoomEntered && numberEntered
    ) {
        reduce(
            newState = currentState.copy(
                findIdButtonEnabled = enabled,
            ),
        )
    }
}


internal sealed class FindIdIntent : MviIntent {
    object FindId : FindIdIntent()
    object FetchSchools : FindIdIntent()
    class UpdateGrade(val newGrade: String) : FindIdIntent()
    class UpdateClassRoom(val newClassRoom: String) : FindIdIntent()
    class UpdateNumber(val newNumber: String) : FindIdIntent()
    class UpdateName(val newName: String) : FindIdIntent()

}

internal data class FindIdState(
    val grade: String,
    val classRoom: String,
    val number: String,
    val name: String,
    val gradeError: Boolean,
    val classRoomError: Boolean,
    val numberError: Boolean,
    val nameError: Boolean,
    val findIdButtonEnabled: Boolean,
    val schools: FetchSchoolsOutput.SchoolInformation?,
    val selectedSchool: FetchSchoolsOutput.SchoolInformation?,
) : MviState {
    companion object {
        fun initial() = FindIdState(
            grade = "",
            classRoom = "",
            number = "",
            name = "",
            gradeError = false,
            classRoomError = false,
            numberError = false,
            nameError = false,
            findIdButtonEnabled = false,
            schools = null,
            selectedSchool = null,
        )
    }
}

internal sealed class FindIdSideEffect : MviSideEffect {
    class FindIdSuccess(val email: String) : FindIdSideEffect()
    object BadRequest : FindIdSideEffect()
    object NotFound : FindIdSideEffect()

}
