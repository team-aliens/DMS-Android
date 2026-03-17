package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.di.BaseUrl
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.feature.signup.model.SignUpData
import javax.inject.Inject

@HiltViewModel
internal class TermsViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    @BaseUrl val baseUrl: String,
) : BaseStateViewModel<TermsState, TermsSideEffect>(TermsState()) {

    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
    }

    internal fun postSignUp() {
        viewModelScope.launch {
            with(currentSignUpData) {
                setState { it.copy(isLoading = true, buttonEnabled = false) }
                studentRepository.signUp(
                    schoolVerificationCode = schoolCode,
                    schoolVerificationAnswer = schoolAnswer,
                    email = email,
                    emailVerificationCode = authCode,
                    grade = grade,
                    classRoom = classRoom,
                    number = number,
                    accountId = accountId,
                    password = password,
                    profileImageUrl = profileImageUrl,
                ).onSuccess {
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    sendEffect(TermsSideEffect.NavigateToComplete)
                }.onFailure {
                    setState { it.copy(isLoading = false, buttonEnabled = true) }
                    sendEffect(TermsSideEffect.FailSignUp)
                }
            }
        }
    }

    internal fun setButtonEnabled(enabled: Boolean) {
        setState { it.copy(buttonEnabled = enabled) }
    }
}

internal data class TermsState(
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface TermsSideEffect {
    data object NavigateToComplete : TermsSideEffect
    data object FailSignUp : TermsSideEffect
}
