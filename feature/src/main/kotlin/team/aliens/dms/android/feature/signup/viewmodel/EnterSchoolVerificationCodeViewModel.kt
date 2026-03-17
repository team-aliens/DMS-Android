package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSource
import javax.inject.Inject

private const val SCHOOL_VERIFICATION_CODE_LENGTH = 8

@HiltViewModel
internal class EnterSchoolVerificationCodeViewModel @Inject constructor(
    private val networkSchoolDataSource: NetworkSchoolDataSource,
) : BaseStateViewModel<EnterSchoolVerificationCodeState, EnterSchoolVerificationCodeSideEffect>(
    EnterSchoolVerificationCodeState()
) {

    internal fun setVerificationCode(verificationCode: String) {
        setState { it.copy(verificationCode = verificationCode) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        it.copy(buttonEnabled = it.verificationCode.length == SCHOOL_VERIFICATION_CODE_LENGTH)
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            runCatching {
                networkSchoolDataSource.examineSchoolVerificationCode(uiState.value.verificationCode)
            }.onSuccess { response ->
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(
                    EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion(
                        signUpData = SignUpData(
                            schoolId = response.schoolId.toString(),
                            schoolCode = uiState.value.verificationCode,
                        )
                    )
                )
            }.onFailure {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(EnterSchoolVerificationCodeSideEffect.ShowErrorSnackBar)
            }
        }
    }
}

internal data class EnterSchoolVerificationCodeState(
    val verificationCode: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface EnterSchoolVerificationCodeSideEffect {
    data class MoveToEnterSchoolVerificationQuestion(val signUpData: SignUpData) :
        EnterSchoolVerificationCodeSideEffect
    data object ShowErrorSnackBar : EnterSchoolVerificationCodeSideEffect
}
