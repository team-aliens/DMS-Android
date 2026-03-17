package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSource
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class EnterSchoolVerificationQuestionViewModel @Inject constructor(
    private val networkSchoolDataSource: NetworkSchoolDataSource,
) : BaseStateViewModel<EnterSchoolVerificationQuestionState, EnterSchoolVerificationQuestionSideEffect>(
    EnterSchoolVerificationQuestionState()
) {
    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
        fetchQuestion()
    }

    private fun fetchQuestion() {
        viewModelScope.launch {
            runCatching {
                networkSchoolDataSource.fetchSchoolVerificationQuestion(
                    schoolId = UUID.fromString(currentSignUpData.schoolId)
                )
            }.onSuccess { response ->
                setState { it.copy(schoolVerificationQuestion = response.question) }
            }.onFailure {
                sendEffect(EnterSchoolVerificationQuestionSideEffect.ShowQuestionErrorSnackBar)
            }
        }
    }

    internal fun setSchoolVerificationAnswer(answer: String) {
        setState { it.copy(schoolVerificationAnswer = answer, buttonEnabled = answer.isNotEmpty()) }
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true) }
            runCatching {
                networkSchoolDataSource.examineSchoolVerificationQuestion(
                    schoolId = UUID.fromString(currentSignUpData.schoolId),
                    answer = uiState.value.schoolVerificationAnswer,
                )
            }.onSuccess {
                setState { it.copy(isLoading = false) }
                sendEffect(
                    EnterSchoolVerificationQuestionSideEffect.MoveToEnterEmail(
                        signUpData = currentSignUpData.copy(
                            schoolAnswer = uiState.value.schoolVerificationAnswer,
                        )
                    )
                )
            }.onFailure {
                setState { it.copy(isLoading = false) }
                sendEffect(EnterSchoolVerificationQuestionSideEffect.ShowErrorSnackBar)
            }
        }
    }
}

internal data class EnterSchoolVerificationQuestionState(
    val schoolVerificationQuestion: String = "",
    val schoolVerificationAnswer: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface EnterSchoolVerificationQuestionSideEffect {
    data class MoveToEnterEmail(val signUpData: SignUpData) : EnterSchoolVerificationQuestionSideEffect
    data object ShowErrorSnackBar : EnterSchoolVerificationQuestionSideEffect
    data object ShowQuestionErrorSnackBar : EnterSchoolVerificationQuestionSideEffect
}
