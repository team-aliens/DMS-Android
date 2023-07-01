package team.aliens.dms_android.feature.signup

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.exception.RemoteException
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain.model.student.CheckEmailDuplicationInput
import team.aliens.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.domain.usecase.file.UploadFileUseCase
import team.aliens.domain.usecase.school.ExamineSchoolVerificationCodeUseCase
import team.aliens.domain.usecase.school.ExamineSchoolVerificationQuestionUseCase
import team.aliens.domain.usecase.school.FetchSchoolVerificationQuestionUseCase
import team.aliens.domain.usecase.student.CheckEmailDuplicationUseCase
import team.aliens.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.domain.usecase.student.ExamineStudentNumberUseCase
import team.aliens.domain.usecase.student.SignUpUseCase

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val examineSchoolVerificationCodeUseCase: ExamineSchoolVerificationCodeUseCase,
    private val fetchSchoolVerificationQuestionUseCase: FetchSchoolVerificationQuestionUseCase,
    private val examineSchoolVerificationQuestionUseCase: ExamineSchoolVerificationQuestionUseCase,
    private val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,

    // 학생 학번 인증 && 아이디 중복 체크
    private val examineStudentNumberUseCase: ExamineStudentNumberUseCase,
    private val checkIdDuplicationUseCase: CheckIdDuplicationUseCase,

    // 학생 프로필 설정
    private val uploadFileUseCase: UploadFileUseCase,

    // 최종 회원가입
    private val signUpUseCase: SignUpUseCase,
) : BaseMviViewModel<SignUpIntent, SignUpState, SignUpSideEffect>(
    initialState = SignUpState.initial(),
) {

    override fun processIntent(
        intent: SignUpIntent,
    ) {
        when (intent) {
            is SignUpIntent.VerifySchool -> {
                when (intent) {
                    is SignUpIntent.VerifySchool.SetSchoolVerificationCode -> {
                        setSchoolCode(schoolCode = intent.schoolCode)
                    }

                    is SignUpIntent.VerifySchool.ExamineSchoolVerificationCode -> {
                        examineSchoolVerificationCode()
                    }
                }
            }

            is SignUpIntent.SchoolQuestion -> {
                when (intent) {
                    is SignUpIntent.SchoolQuestion.FetchSchoolQuestion -> {
                        fetchSchoolQuestion()
                    }

                    is SignUpIntent.SchoolQuestion.SetSchoolAnswer -> {
                        setSchoolAnswer(schoolAnswer = intent.schoolAnswer)
                    }

                    is SignUpIntent.SchoolQuestion.ExamineSchoolAnswer -> {
                        examineSchoolAnswer()
                    }
                }
            }

            is SignUpIntent.SendEmail -> {
                when (intent) {
                    is SignUpIntent.SendEmail.SetEmail -> {
                        setEmail(email = intent.email)
                    }

                    is SignUpIntent.SendEmail.CheckEmailDuplication -> {
                        checkEmailDuplication()
                    }

                    is SignUpIntent.SendEmail.SendEmailVerificationCode -> {
                        sendEmailVerificationCode()
                    }

                    is SignUpIntent.SendEmail.SetEmailExpirationTime -> {
                        setEmailExpirationTime(emailExpirationTime = intent.emailExpirationTime)
                    }

                    is SignUpIntent.SendEmail.SetEmailTimerWorked -> {
                        setEmailTimerWorked(emailTimerWorked = intent.emailTimerWorked)
                    }
                }
            }

            is SignUpIntent.VerifyEmail -> {
                when (intent) {
                    is SignUpIntent.VerifyEmail.SetAuthCode -> {
                        setAuthCode(authCode = intent.authCode)
                    }

                    is SignUpIntent.VerifyEmail.CheckEmailVerificationCode -> {
                        checkEmailVerificationCode()
                    }
                }
            }

            is SignUpIntent.SetEmailVerificationCode -> {
                setAuthCode(authCode = intent.emailVerificationCode)
            }

            is SignUpIntent.SetGrade -> {
                setGrade(grade = intent.grade)
            }

            is SignUpIntent.SetClassRoom -> {
                setClassRoom(classRoom = intent.classRoom)
            }

            is SignUpIntent.SetNumber -> {
                setNumber(number = intent.number)
            }

            is SignUpIntent.SetAccountId -> {
                setAccountId(accountId = intent.accountId)
            }

            is SignUpIntent.SetPassword -> {
                setPassword(password = intent.password)
            }

            is SignUpIntent.SetPasswordRepeat -> {
                setPasswordRepeat(passwordRepeat = intent.passwordRepeat)
            }
        }
    }

    private fun examineSchoolVerificationCode() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                examineSchoolVerificationCodeUseCase(
                    examineSchoolVerificationCodeInput = ExamineSchoolVerificationCodeInput(
                        schoolCode = stateFlow.value.schoolCode
                    )
                )
            }.onSuccess {
                postSideEffect(SignUpSideEffect.VerifySchool.SuccessVerifySchoolCode)
                setSchoolId(schoolId = it.schoolId)
            }.onFailure {
                setSchoolCodeMismatchError(it is RemoteException.Unauthorized)
            }
        }
    }

    private fun fetchSchoolQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSchoolVerificationQuestionUseCase(
                    fetchSchoolVerificationQuestionInput = FetchSchoolVerificationQuestionInput(
                        schoolId = stateFlow.value.schoolId!!,
                    )
                )
            }.onSuccess {
                setSchoolQuestion(schoolQuestion = it.question)
            }.onFailure {

            }
        }
    }

    private fun examineSchoolAnswer() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                examineSchoolVerificationQuestionUseCase(
                    examineSchoolVerificationQuestionInput = ExamineSchoolVerificationQuestionInput(
                        schoolId = stateFlow.value.schoolId!!,
                        answer = stateFlow.value.schoolAnswer,
                    )
                )
            }.onSuccess {
                postSideEffect(sideEffect = SignUpSideEffect.SchoolQuestion.SuccessVerifySchoolAnswer)
            }.onFailure {
                setSchoolAnswerMismatchError(it is RemoteException.Unauthorized)
            }
        }
    }

    private fun checkEmailDuplication() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                checkEmailDuplicationUseCase(
                    checkEmailDuplicationInput = CheckEmailDuplicationInput(stateFlow.value.email)
                )
            }.onSuccess {
                postSideEffect(sideEffect = SignUpSideEffect.SendEmail.AvailableEmail)
            }.onFailure {
                if (it is RemoteException.Conflict) {
                    postSideEffect(sideEffect = SignUpSideEffect.SendEmail.DuplicatedEmail)
                }
            }
        }
    }

    private fun sendEmailVerificationCode() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                sendEmailVerificationCodeUseCase(
                    sendEmailVerificationCodeInput = SendEmailVerificationCodeInput(
                        email = stateFlow.value.email,
                        type = EmailVerificationType.SIGNUP,
                    )
                )
            }.onSuccess {
                postSideEffect(sideEffect = SignUpSideEffect.SendEmail.SuccessSendEmailVerificationCode)
            }.onFailure {

            }
        }
    }

    private fun checkEmailVerificationCode() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                checkEmailVerificationCodeUseCase(
                    checkEmailVerificationCodeInput = CheckEmailVerificationCodeInput(
                        email = stateFlow.value.email,
                        authCode = stateFlow.value.authCode,
                        type = EmailVerificationType.SIGNUP,
                    )
                )
            }.onSuccess {
                postSideEffect(sideEffect = SignUpSideEffect.VerifyEmail.SuccessVerifyEmail)
            }.onFailure {
                setAuthCodeMismatchError(it is RemoteException.Unauthorized)
            }
        }
    }

    private fun setSchoolCode(
        schoolCode: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolCode = schoolCode,
            )
        )
        if (schoolCode.length == 8) examineSchoolVerificationCode()
        setSchoolCodeConfirmButtonEnabled(schoolCode.isNotEmpty() && !stateFlow.value.schoolCodeMismatchError)
    }

    private fun setSchoolCodeMismatchError(
        schoolCodeMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolCodeMismatchError = schoolCodeMismatchError,
            )
        )
    }

    private fun setSchoolCodeConfirmButtonEnabled(
        schoolCodeConfirmButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolCodeConfirmButtonEnabled = schoolCodeConfirmButtonEnabled,
            )
        )
    }

    private fun setSchoolQuestion(
        schoolQuestion: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolQuestion = schoolQuestion,
            )
        )
    }

    private fun setSchoolAnswer(
        schoolAnswer: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolAnswer = schoolAnswer,
            )
        )
        setSchoolAnswerConfirmButtonEnabled(schoolAnswer.isNotEmpty() && !stateFlow.value.schoolAnswerMismatchError)
    }

    private fun setSchoolAnswerMismatchError(
        schoolAnswerMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolAnswerMismatchError = schoolAnswerMismatchError
            )
        )
    }

    private fun setSchoolAnswerConfirmButtonEnabled(
        schoolAnswerConfirmButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolAnswerConfirmButtonEnabled = schoolAnswerConfirmButtonEnabled,
            )
        )
    }

    private fun setEmail(
        email: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                email = email,
            )
        )
        setEmailFormatError(
            email.isNotEmpty() && !Pattern.matches(
                Patterns.EMAIL_ADDRESS.pattern(),
                email
            )
        )
        setSendEmailButtonEnabled(email.isNotEmpty() && !stateFlow.value.emailFormatError)
    }

    private fun setEmailFormatError(
        emailFormatError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                emailFormatError = emailFormatError,
            )
        )
    }

    private fun setSendEmailButtonEnabled(
        sendEmailButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                sendEmailButtonEnabled = sendEmailButtonEnabled,
            )
        )
    }

    private fun setEmailExpirationTime(
        emailExpirationTime: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                emailExpirationTime = emailExpirationTime,
            )
        )
    }

    private fun setEmailTimerWorked(
        emailTimerWorked: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                emailTimerWorked = emailTimerWorked,
            )
        )
    }

    private fun setAuthCode(
        authCode: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                authCode = authCode,
            )
        )
        setAuthCodeConfirmButtonEnabled(authCode.isNotEmpty() && !stateFlow.value.authCodeMismatchError)
    }

    private fun setAuthCodeMismatchError(
        authCodeMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                authCodeMismatchError = authCodeMismatchError,
            )
        )
    }

    private fun setAuthCodeConfirmButtonEnabled(
        authCodeConfirmButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                authCodeConfirmButtonEnabled = authCodeConfirmButtonEnabled,
            )
        )
    }

    private fun setGrade(
        grade: Int,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                grade = grade,
            )
        )
    }

    private fun setClassRoom(
        classRoom: Int,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                classRoom = classRoom,
            )
        )
    }

    private fun setNumber(
        number: Int,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                number = number,
            )
        )
    }

    private fun setAccountId(
        accountId: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                accountId = accountId,
            )
        )
    }

    private fun setPassword(
        password: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                password = password,
            )
        )
    }

    private fun setPasswordRepeat(
        passwordRepeat: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                passwordRepeat = passwordRepeat,
            )
        )
    }

    private fun setProfileImageUrl(
        profileImageUrl: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                profileImageUrl = profileImageUrl,
            )
        )
    }

    private fun setSchoolId(
        schoolId: UUID,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolId = schoolId,
            )
        )
    }
}

sealed class SignUpIntent : MviIntent {

    sealed class VerifySchool : SignUpIntent() {
        class SetSchoolVerificationCode(val schoolCode: String) : VerifySchool()
        object ExamineSchoolVerificationCode : VerifySchool()
    }

    sealed class SchoolQuestion : SignUpIntent() {
        object FetchSchoolQuestion : SchoolQuestion()
        class SetSchoolAnswer(val schoolAnswer: String) : SchoolQuestion()
        object ExamineSchoolAnswer : SchoolQuestion()
    }

    sealed class SendEmail : SignUpIntent() {
        class SetEmail(val email: String) : SendEmail()
        object CheckEmailDuplication : SendEmail()
        object SendEmailVerificationCode : SendEmail()
        class SetEmailExpirationTime(val emailExpirationTime: String) : SendEmail()
        class SetEmailTimerWorked(val emailTimerWorked: Boolean) : SendEmail()
    }

    sealed class VerifyEmail : SignUpIntent() {
        class SetAuthCode(val authCode: String) : VerifyEmail()
        object CheckEmailVerificationCode : VerifyEmail()
    }

    class SetEmailVerificationCode(val emailVerificationCode: String) : SignUpIntent()
    class SetGrade(val grade: Int) : SignUpIntent()
    class SetClassRoom(val classRoom: Int) : SignUpIntent()
    class SetNumber(val number: Int) : SignUpIntent()
    class SetAccountId(val accountId: String) : SignUpIntent()
    class SetPassword(val password: String) : SignUpIntent()
    class SetPasswordRepeat(val passwordRepeat: String) : SignUpIntent()
}

data class SignUpState(
    val schoolCode: String,
    val schoolCodeMismatchError: Boolean,
    val schoolCodeConfirmButtonEnabled: Boolean,

    val schoolQuestion: String,
    val schoolAnswer: String,
    val schoolAnswerMismatchError: Boolean,
    val schoolAnswerConfirmButtonEnabled: Boolean,

    val email: String,
    val emailFormatError: Boolean,
    val sendEmailButtonEnabled: Boolean,
    val emailExpirationTime: String,
    val emailTimerWorked: Boolean,

    val authCode: String,
    val authCodeMismatchError: Boolean,
    val authCodeConfirmButtonEnabled: Boolean,

    val grade: Int,
    val classRoom: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val passwordRepeat: String,
    val profileImageUrl: String,
    val schoolId: UUID?,
) : MviState {
    companion object {
        fun initial(): SignUpState {
            return SignUpState(
                schoolCode = "",
                schoolCodeMismatchError = false,
                schoolCodeConfirmButtonEnabled = false,

                schoolQuestion = "",
                schoolAnswer = "",
                schoolAnswerMismatchError = false,
                schoolAnswerConfirmButtonEnabled = false,

                email = "",
                emailFormatError = false,
                sendEmailButtonEnabled = false,
                emailExpirationTime = "",
                emailTimerWorked = false,

                authCode = "",
                authCodeMismatchError = false,
                authCodeConfirmButtonEnabled = false,

                grade = 0,
                classRoom = 0,
                number = 0,
                accountId = "",
                password = "",
                passwordRepeat = "",
                profileImageUrl = "",
                schoolId = null,
            )
        }
    }
}

sealed class SignUpSideEffect : MviSideEffect {

    sealed class VerifySchool : SignUpSideEffect() {
        object SuccessVerifySchoolCode : VerifySchool()
    }

    sealed class SchoolQuestion : SignUpSideEffect() {
        object SuccessVerifySchoolAnswer : SchoolQuestion()
    }

    sealed class SendEmail : SignUpSideEffect() {
        object AvailableEmail : SendEmail()
        object DuplicatedEmail : SendEmail()
        object SuccessSendEmailVerificationCode : SendEmail()
    }

    sealed class VerifyEmail : SignUpSideEffect() {
        object SuccessVerifyEmail : VerifyEmail()
    }

    object NotCorrectEmailVerificationCode : SignUpSideEffect()
    object NotExistsStudentNumber : SignUpSideEffect()
    object DuplicatedStudent : SignUpSideEffect()
    object MisMatchPasswordRepeat : SignUpSideEffect()
    object SuccessSetProfileImage : SignUpSideEffect()
    object SuccessSignUp : SignUpSideEffect()
}

