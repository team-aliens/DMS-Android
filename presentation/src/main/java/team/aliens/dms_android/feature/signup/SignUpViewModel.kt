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
import team.aliens.domain.model.student.CheckIdDuplicationInput
import team.aliens.domain.model.student.ExamineStudentNumberInput
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

            is SignUpIntent.SetId -> {
                when (intent) {
                    is SignUpIntent.SetId.SetGrade -> {
                        setGrade(grade = intent.grade)
                    }

                    is SignUpIntent.SetId.SetClassRoom -> {
                        setClassRoom(classRoom = intent.classRoom)
                    }

                    is SignUpIntent.SetId.SetNumber -> {
                        setNumber(number = intent.number)
                    }

                    is SignUpIntent.SetId.SetAccountId -> {
                        setAccountId(accountId = intent.accountId)
                    }

                    is SignUpIntent.SetId.SetStudentName -> {
                        setStudentName(studentName = intent.studentName)
                    }

                    is SignUpIntent.SetId.SetGcnMismatchError -> {
                        setStudentNumberMismatchError(studentNumberMismatchError = intent.gcnMismatchError)
                    }

                    is SignUpIntent.SetId.ExamineStudentNumber -> {
                        examineStudentNumber()
                    }

                    is SignUpIntent.SetId.CheckIdDuplication -> {
                        checkIdDuplication()
                    }

                    is SignUpIntent.SetId.CheckedStudentName -> {
                        setCheckedStudentName(intent.checkedStudentName)
                    }
                }
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
                if (it is RemoteException.Conflict) {
                    postSideEffect(SignUpSideEffect.VerifyEmail.ConflictEmail)
                }
            }
        }
    }

    private fun examineStudentNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                examineStudentNumberUseCase(
                    examineStudentNumberInput = ExamineStudentNumberInput(
                        schoolId = stateFlow.value.schoolId!!,
                        grade = stateFlow.value.grade.toInt(),
                        classRoom = stateFlow.value.classRoom.toInt(),
                        number = stateFlow.value.number.toInt(),
                    )
                )
            }.onSuccess {
                setSuccessVerifyStudent(true)
                setStudentName(studentName = it.name)
            }.onFailure {
                setSuccessVerifyStudent(false)
                when (it) {
                    is RemoteException.NotFound -> {
                        setStudentNumberMismatchError(true)
                    }

                    is RemoteException.Conflict -> {
                        postSideEffect(SignUpSideEffect.SetId.ConflictStudent)
                    }
                }
            }
        }
    }

    private fun checkIdDuplication() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                checkIdDuplicationUseCase(
                    checkIdDuplicationInput = CheckIdDuplicationInput(
                        accountId = stateFlow.value.accountId,
                    )
                )
            }.onSuccess {
                postSideEffect(SignUpSideEffect.SetId.SuccessVerifyStudent)
            }.onFailure {
                setConflictAccountIdError(it is RemoteException.Conflict)
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
        if (authCode.length == 6) postSideEffect(SignUpSideEffect.VerifyEmail.CompleteEnteredAuthCode)
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
        grade: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                grade = grade,
            )
        )
        setIdConfirmButtonEnabled()
    }

    private fun setClassRoom(
        classRoom: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                classRoom = classRoom,
            )
        )
        setIdConfirmButtonEnabled()
    }

    private fun setNumber(
        number: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                number = number,
            )
        )
        setIdConfirmButtonEnabled()
    }

    private fun setAccountId(
        accountId: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                accountId = accountId,
            )
        )
        setIdConfirmButtonEnabled()
    }

    private fun setStudentName(
        studentName: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                studentName = studentName,
            )
        )
    }

    private fun setStudentNumberMismatchError(
        studentNumberMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                studentNumberMismatchError = studentNumberMismatchError,
            )
        )
    }

    private fun setSuccessVerifyStudent(
        successVerifyStudent: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                successVerifyStudent = successVerifyStudent,
            )
        )
    }

    private fun setConflictAccountIdError(
        conflictAccountIdError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                conflictAccountIdError = conflictAccountIdError,
            )
        )
    }

    private fun setIdConfirmButtonEnabled() {
        with(stateFlow.value) {
            reduce(
                newState = stateFlow.value.copy(
                    idConfirmButtonEnabled = grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty() && accountId.isNotEmpty()
                )
            )
        }
    }

    private fun setCheckedStudentName(
        checkedStudentName: Boolean,
    ){
        reduce(
            newState = stateFlow.value.copy(
                checkedStudentName = checkedStudentName,
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

    sealed class SetId : SignUpIntent() {
        class SetGrade(val grade: String) : SetId()
        class SetClassRoom(val classRoom: String) : SetId()
        class SetNumber(val number: String) : SetId()
        class SetAccountId(val accountId: String) : SetId()
        class SetStudentName(val studentName: String) : SetId()
        class SetGcnMismatchError(val gcnMismatchError: Boolean) : SetId()
        object ExamineStudentNumber : SetId()
        object CheckIdDuplication : SetId()
        class CheckedStudentName(val checkedStudentName: Boolean): SetId()
    }

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

    val grade: String,
    val classRoom: String,
    val number: String,
    val accountId: String,
    val studentName: String,
    val studentNumberMismatchError: Boolean,
    val successVerifyStudent: Boolean,
    val conflictAccountIdError: Boolean,
    val checkedStudentName: Boolean,
    val idConfirmButtonEnabled: Boolean,

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

                grade = "",
                classRoom = "",
                number = "",
                accountId = "",
                studentName = "",
                studentNumberMismatchError = false,
                successVerifyStudent = false,
                conflictAccountIdError = false,
                idConfirmButtonEnabled = false,
                checkedStudentName = false,

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
        object CompleteEnteredAuthCode : VerifyEmail()
        object ConflictEmail : VerifyEmail()
    }

    sealed class SetId : SignUpSideEffect() {
        object ConflictStudent : SetId()
        object SuccessVerifyStudent : SetId()
    }

    object NotCorrectEmailVerificationCode : SignUpSideEffect()
    object NotExistsStudentNumber : SignUpSideEffect()
    object DuplicatedStudent : SignUpSideEffect()
    object MisMatchPasswordRepeat : SignUpSideEffect()
    object SuccessSetProfileImage : SignUpSideEffect()
    object SuccessSignUp : SignUpSideEffect()
}

