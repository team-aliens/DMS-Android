package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.feature.signup.model.SignUpData
import javax.inject.Inject

@HiltViewModel
internal class EnterEmailViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<EnterEmailState, EnterEmailSideEffect>(EnterEmailState()) {

    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
    }

    internal fun setEmail(email: String) {
        setState { it.copy(email = email, buttonEnabled = email.isNotEmpty()) }
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            studentRepository.checkEmailDuplication(uiState.value.email)
                .onSuccess {
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    sendEffect(
                        EnterEmailSideEffect.MoveToEnterEmailVerificationCode(
                            signUpData = currentSignUpData.copy(email = uiState.value.email)
                        )
                    )
                }.onFailure { exception ->
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    when (exception) {
                        is ConflictException -> sendEffect(EnterEmailSideEffect.ShowConflictSnackBar)
                        else -> sendEffect(EnterEmailSideEffect.ShowErrorSnackBar)
                    }
                }
        }
    }
}

internal data class EnterEmailState(
    val email: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface EnterEmailSideEffect {
    data class MoveToEnterEmailVerificationCode(val signUpData: SignUpData) : EnterEmailSideEffect
    data object ShowConflictSnackBar : EnterEmailSideEffect
    data object ShowErrorSnackBar : EnterEmailSideEffect
}
