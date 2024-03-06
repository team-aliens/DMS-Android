package team.aliens.dms.android.feature.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.school.repository.SchoolRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val schoolRepository: SchoolRepository,
    private val authRepository: AuthRepository,
) : BaseMviViewModel<SignUpUiState, SignUpIntent, SignUpSideEffect>(
    initialState = SignUpUiState.initial(),
) {
    private lateinit var schoolId: UUID
    override fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UpdateSchoolVerificationCode -> updateSchoolVerificationCode(value = intent.value)
            SignUpIntent.ExamineSchoolVerificationCode -> examineSchoolVerificationCode()
            is SignUpIntent.UpdateSchoolVerificationAnswer -> updateSchoolVerificationQuestion(value = intent.value)
            SignUpIntent.ExamineSchoolVerificationAnswer -> examineSchoolVerificationQuestion()
            is SignUpIntent.UpdateEmail -> updateEmail(value = intent.value)
            SignUpIntent.VerifyEmail -> checkEmailAvailable()
            is SignUpIntent.UpdateEmailVerificationCode -> updateEmailVerificationCode(value = intent.value)
            SignUpIntent.CheckEmailVerificationCode -> checkEmailVerificationCode()
            is SignUpIntent.UpdateClass -> updateClass(value = intent.value)
            is SignUpIntent.UpdateGrade -> updateGrade(value = intent.value)
            is SignUpIntent.UpdateNumber -> updateNumber(value = intent.value)
            SignUpIntent.ExamineStudent -> examineStudent()
            is SignUpIntent.UpdateId -> updateId(value = intent.value)
            SignUpIntent.ResetEmailVerificationSession -> resetEmailVerificationSession()
            SignUpIntent.ConfirmId -> confirmId()
            is SignUpIntent.UpdatePassword -> updatePassword(value = intent.value)
            is SignUpIntent.UpdatePasswordRepeat -> updatePasswordRepeat(value = intent.value)
            SignUpIntent.ConfirmPassword -> confirmPassword()
        }
    }

    private fun updateSchoolVerificationCode(value: String) = run {
        if (value.length > SCHOOL_VERIFICATION_CODE_LENGTH) {
            return@run false
        }
        reduce(newState = stateFlow.value.copy(schoolVerificationCode = value))
    }

    private fun examineSchoolVerificationCode() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            schoolRepository.examineSchoolVerificationCode(code = stateFlow.value.schoolVerificationCode)
        }.onSuccess { schoolId ->
            this@SignUpViewModel.schoolId = schoolId
            this@SignUpViewModel.fetchSchoolVerificationQuestion()
            postSideEffect(SignUpSideEffect.SchoolVerificationCodeExamined)
        }.onFailure {
            postSideEffect(SignUpSideEffect.SchoolVerificationCodeNotFound)
            reduce(newState = stateFlow.value.copy(schoolVerificationCode = ""))
        }
    }

    private fun fetchSchoolVerificationQuestion() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            schoolRepository.fetchSchoolVerificationQuestion(
                schoolId = this@SignUpViewModel.schoolId,
            )
        }.onSuccess { fetchedSchoolVerificationQuestion ->
            reduce(newState = stateFlow.value.copy(schoolVerificationQuestion = fetchedSchoolVerificationQuestion))
        }.onFailure {
            // TODO
        }
    }

    private fun updateSchoolVerificationQuestion(value: String) =
        reduce(newState = stateFlow.value.copy(schoolVerificationAnswer = value))

    private fun examineSchoolVerificationQuestion() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value
        runCatching {
            schoolRepository.examineSchoolVerificationQuestion(
                schoolId = this@SignUpViewModel.schoolId,
                answer = capturedState.schoolVerificationAnswer,
            )
        }.onSuccess {
            postSideEffect(SignUpSideEffect.SchoolVerificationQuestionExamined)
        }.onFailure {
            // TODO: handle error type
            postSideEffect(SignUpSideEffect.SchoolVerificationQuestionIncorrect)
        }
    }

    private fun updateEmail(value: String) = reduce(newState = stateFlow.value.copy(email = value))

    private fun checkEmailAvailable() = viewModelScope.launch(Dispatchers.IO) {
        val email = stateFlow.value.email
        runCatching {
            studentRepository.checkEmailDuplication(email)
        }.onSuccess {
            runCatching {
                this@SignUpViewModel.sendEmailVerificationCode(email = email)
            }.onSuccess {
                postSideEffect(SignUpSideEffect.EmailAvailable)
            }.recover { throw it }
        }.onFailure {
            // TODO: handle error type
            postSideEffect(SignUpSideEffect.EmailFormatError)
        }
    }

    private suspend fun sendEmailVerificationCode(email: String) =
        authRepository.sendEmailVerificationCode(
            email = email,
            type = EmailVerificationType.SIGNUP,
        )

    private fun updateEmailVerificationCode(value: String) = run {
        if (value.length > EMAIL_VERIFICATION_CODE_LENGTH) {
            return@run false
        }
        reduce(newState = stateFlow.value.copy(emailVerificationCode = value))
    }

    private fun checkEmailVerificationCode() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value
        runCatching {
            authRepository.checkEmailVerificationCode(
                email = capturedState.email,
                code = capturedState.emailVerificationCode,
                type = EmailVerificationType.SIGNUP,
            )
        }.onSuccess {
            postSideEffect(SignUpSideEffect.EmailVerificationCodeChecked)
        }.onFailure {
            postSideEffect(SignUpSideEffect.EmailVerificationCodeIncorrect)
            reduce(newState = stateFlow.value.copy(emailVerificationCode = ""))
        }
    }

    private fun resetEmailVerificationSession() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            this@SignUpViewModel.sendEmailVerificationCode(email = stateFlow.value.email)
        }.onSuccess {
            postSideEffect(SignUpSideEffect.EmailVerificationSessionReset)
            reduce(newState = stateFlow.value.copy(emailVerificationCode = ""))
        }.onFailure {
            postSideEffect(SignUpSideEffect.EmailVerificationSessionResetFailed)
        }
    }

    private fun updateGrade(value: String) = reduce(
        newState = stateFlow.value.copy(
            grade = value,
        ),
    )

    private fun updateClass(value: String) = reduce(
        newState = stateFlow.value.copy(
            classroom = value,
        ),
    )

    private fun updateNumber(value: String) = reduce(
        newState = stateFlow.value.copy(
            number = value,
        ),
    )

    private fun examineStudent() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            val capturedState = stateFlow.value
            studentRepository.examineStudentNumber(
                schoolId = schoolId,
                grade = capturedState.grade.toInt(),
                classroom = capturedState.classroom.toInt(),
                number = capturedState.number.toInt(),
            )
        }.onSuccess { studentName ->
            postSideEffect(SignUpSideEffect.UserFound(studentName = studentName))
        }.onFailure {
            it.printStackTrace()
            postSideEffect(SignUpSideEffect.UserNotFound)
        }
    }

    private fun updateId(value: String) = reduce(
        newState = stateFlow.value.copy(
            id = value,
        ),
    )

    private fun confirmId() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studentRepository.checkIdDuplication(id = stateFlow.value.id)
        }.onSuccess {
            postSideEffect(SignUpSideEffect.IdAvailable)
        }.onFailure {
            postSideEffect(SignUpSideEffect.IdDuplicated)
        }
    }

    private fun updatePassword(value: String) = reduce(
        newState = stateFlow.value.copy(
            password = value,
        ),
    )

    private fun updatePasswordRepeat(value: String) = reduce(
        newState = stateFlow.value.copy(
            passwordRepeat = value,
        ),
    )

    private fun confirmPassword() {
        val capturedState = stateFlow.value
        val password = capturedState.password
        val passwordRepeat = capturedState.passwordRepeat
        postSideEffect(
            sideEffect = if (password != passwordRepeat) {
                SignUpSideEffect.PasswordMismatch
            } else {
                SignUpSideEffect.PasswordSet
            },
        )
    }

    companion object {
        const val SCHOOL_VERIFICATION_CODE_LENGTH = 8
        const val EMAIL_VERIFICATION_CODE_LENGTH = 6
    }
}

data class SignUpUiState(
    // EnterSchoolVerificationCode
    val schoolVerificationCode: String,

    // EnterSchoolVerificationQuestion
    val schoolVerificationQuestion: String?,
    val schoolVerificationAnswer: String,

    // EnterEmail
    val email: String,

    // EnterEmailVerificationCode
    val sessionId: UUID,
    val emailVerificationCode: String,

    // SetId
    val grade: String,
    val classroom: String,
    val number: String,
    val id: String,

    // SetPassword
    val password: String,
    val passwordRepeat: String,
) : UiState() {
    companion object {
        fun initial() = SignUpUiState(
            schoolVerificationCode = "",
            schoolVerificationQuestion = null,
            schoolVerificationAnswer = "",
            email = "",
            sessionId = UUID.randomUUID(),
            emailVerificationCode = "",
            grade = "",
            classroom = "",
            number = "",
            id = "",
            password = "",
            passwordRepeat = "",
        )
    }
}

sealed class SignUpIntent : Intent() {
    // EnterSchoolVerificationCode
    class UpdateSchoolVerificationCode(val value: String) : SignUpIntent()
    data object ExamineSchoolVerificationCode : SignUpIntent()

    // EnterSchoolVerificationQuestion
    class UpdateSchoolVerificationAnswer(val value: String) : SignUpIntent()
    data object ExamineSchoolVerificationAnswer : SignUpIntent()

    // EnterEmail
    class UpdateEmail(val value: String) : SignUpIntent()
    data object VerifyEmail : SignUpIntent()

    // EnterEmailVerificationCode
    class UpdateEmailVerificationCode(val value: String) : SignUpIntent()
    data object CheckEmailVerificationCode : SignUpIntent()
    data object ResetEmailVerificationSession : SignUpIntent()

    // SetId
    class UpdateGrade(val value: String) : SignUpIntent()
    class UpdateClass(val value: String) : SignUpIntent()
    class UpdateNumber(val value: String) : SignUpIntent()
    data object ExamineStudent : SignUpIntent()
    class UpdateId(val value: String) : SignUpIntent()
    data object ConfirmId : SignUpIntent()

    // SetPassword
    class UpdatePassword(val value: String) : SignUpIntent()
    class UpdatePasswordRepeat(val value: String) : SignUpIntent()
    data object ConfirmPassword : SignUpIntent()
}

sealed class SignUpSideEffect : SideEffect() {

    // EnterSchoolVerificationCode
    data object SchoolVerificationCodeExamined : SignUpSideEffect()
    data object SchoolVerificationCodeNotFound : SignUpSideEffect()

    // EnterSchoolVerificationQuestion
    data object SchoolVerificationQuestionExamined : SignUpSideEffect()
    data object SchoolVerificationQuestionIncorrect : SignUpSideEffect()

    // EnterEmail
    data object EmailAvailable : SignUpSideEffect()
    data object EmailFormatError : SignUpSideEffect()

    // EnterEmailVerificationCode
    data object EmailVerificationCodeChecked : SignUpSideEffect()
    data object EmailVerificationCodeIncorrect : SignUpSideEffect()
    data object EmailVerificationSessionReset : SignUpSideEffect()
    data object EmailVerificationSessionResetFailed : SignUpSideEffect()

    // SetId
    class UserFound(val studentName: String) : SignUpSideEffect()
    data object UserNotFound : SignUpSideEffect()
    data object IdAvailable : SignUpSideEffect()
    data object IdDuplicated : SignUpSideEffect()

    // Set Password
    data object PasswordSet : SignUpSideEffect()
    data object PasswordMismatch : SignUpSideEffect()
}
