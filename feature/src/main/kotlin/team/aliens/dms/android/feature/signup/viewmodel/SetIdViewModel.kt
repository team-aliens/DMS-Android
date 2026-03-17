package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.feature.signup.model.SignUpData
import javax.inject.Inject

@HiltViewModel
internal class SetIdViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<SetIdState, SetIdSideEffect>(SetIdState()) {

    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
    }

    internal fun setId(id: String) {
        setState { it.copy(id = id, buttonEnabled = id.isNotEmpty()) }
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            studentRepository.checkIdDuplication(uiState.value.id)
                .onSuccess {
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    sendEffect(
                        SetIdSideEffect.MoveToSetPassword(
                            signUpData = currentSignUpData.copy(accountId = uiState.value.id)
                        )
                    )
                }.onFailure { exception ->
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    when (exception) {
                        is ConflictException -> sendEffect(SetIdSideEffect.ShowConflictSnackBar)
                        else -> sendEffect(SetIdSideEffect.ShowErrorSnackBar)
                    }
                }
        }
    }
}

internal data class SetIdState(
    val id: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface SetIdSideEffect {
    data class MoveToSetPassword(val signUpData: SignUpData) : SetIdSideEffect
    data object ShowConflictSnackBar : SetIdSideEffect
    data object ShowErrorSnackBar : SetIdSideEffect
}
