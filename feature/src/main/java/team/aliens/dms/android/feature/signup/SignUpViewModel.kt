package team.aliens.dms.android.feature.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import javax.inject.Inject

@HiltViewModel
 class SignUpViewModel @Inject constructor(

)  : BaseMviViewModel<SignUpUiState, SignUpIntent, SignUpSideEffect>(
    initialState = SignUpUiState.initial(),
)

data class SignUpUiState(
    val id: String,
) : UiState() {
    companion object {
        fun initial() = SignUpUiState(
            id = "",
        )
    }
}

sealed class SignUpIntent : Intent()

sealed class SignUpSideEffect : SideEffect()

/*

import android.net.Uri
import android.util.Patterns
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain._legacy.exception.RemoteException
import team.aliens.dms.android.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.dms.android.domain.model._common.EmailVerificationType
import team.aliens.dms.android.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.file.UploadFileInput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.student.CheckEmailDuplicationInput
import team.aliens.dms.android.domain.model.student.CheckIdDuplicationInput
import team.aliens.dms.android.domain.model.student.ExamineStudentNumberInput
import team.aliens.dms.android.domain.model.student.SignUpInput
import team.aliens.dms.android.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.dms.android.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.dms.android.domain.usecase.file.UploadFileUseCase
import team.aliens.dms.android.domain.usecase.school.ExamineSchoolVerificationCodeUseCase
import team.aliens.dms.android.domain.usecase.school.ExamineSchoolVerificationQuestionUseCase
import team.aliens.dms.android.domain.usecase.school.FetchSchoolVerificationQuestionUseCase
import team.aliens.dms.android.domain.usecase.student.CheckEmailDuplicationUseCase
import team.aliens.dms.android.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.dms.android.domain.usecase.student.ExamineStudentNumberUseCase
import team.aliens.dms.android.domain.usecase.student.SignUpUseCase

private const val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"
const val defaultProfileUrl =
    "https://image-dms.s3.ap-northeast-2.amazonaws.com/59fd0067-93ef-4bcb-8722-5bc8786c5156%7C%7C%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png"

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val examineSchoolVerificationCodeUseCase: ExamineSchoolVerificationCodeUseCase,
    private val fetchSchoolVerificationQuestionUseCase: FetchSchoolVerificationQuestionUseCase,
    private val examineSchoolVerificationQuestionUseCase: ExamineSchoolVerificationQuestionUseCase,
    private val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,
    private val examineStudentNumberUseCase: ExamineStudentNumberUseCase,
    private val checkIdDuplicationUseCase: CheckIdDuplicationUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
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

                    is SignUpIntent.SetId.ExamineStudentNumber -> {
                        examineStudentNumber()
                    }

                    is SignUpIntent.SetId.CheckIdDuplication -> {
                        checkIdDuplication()
                    }

                    is SignUpIntent.SetId.SetCheckedStudentName -> {
                        this.setCheckedStudentName(intent.checkedStudentName)
                    }
                }
            }

            is SignUpIntent.SetPassword -> {
                when (intent) {
                    is SignUpIntent.SetPassword.SetPassword -> {
                        setPassword(intent.password)
                    }

                    is SignUpIntent.SetPassword.SetPasswordRepeat -> {
                        setPasswordRepeat(intent.passwordRepeat)
                    }

                    is SignUpIntent.SetPassword.CheckPassword -> {
                        checkPassword()
                    }
                }
            }

            is SignUpIntent.SetProfileImage -> {
                when (intent) {
                    is SignUpIntent.SetProfileImage.SelectProfileImage -> {
                        setProfileImageUri(intent.profileImageUri)
                    }

                    is SignUpIntent.SetProfileImage.UploadImage -> {
                        uploadImage()
                    }
                }
            }

            is SignUpIntent.Terms -> {
                when (intent) {
                    is SignUpIntent.Terms.SetCheckedPolicy -> {
                        setCheckedPolicy(intent.checkedPolicy)
                    }

                    is SignUpIntent.Terms.SignUp -> {
                        signUp()
                    }
                }
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
                setSchoolCodeMismatchError(it is team.aliens.dms.android.domain._legacy.exception.RemoteException.Unauthorized)
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
                setSchoolAnswerMismatchError(it is team.aliens.dms.android.domain._legacy.exception.RemoteException.Unauthorized)
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
                setSendEmailButtonEnabled(false)
                if (it is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict) {
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
                setAuthCodeButtonEnabled(true)
                when(it){
                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.Unauthorized -> {
                        setAuthCodeMismatchError()
                    }

                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict -> {
                        postSideEffect(SignUpSideEffect.VerifyEmail.ConflictEmail)
                    }
                }
            }
        }
    }

    private fun examineStudentNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            with(stateFlow.value) {
                kotlin.runCatching {
                    examineStudentNumberUseCase(
                        examineStudentNumberInput = ExamineStudentNumberInput(
                            schoolId = schoolId!!,
                            grade = grade.toInt(),
                            classRoom = classRoom.toInt(),
                            number = number.toInt(),
                        )
                    )
                }.onSuccess {
                    setCheckedStudentName(true)
                    setStudentNumberMismatchError(false)
                    setStudentName(studentName = it.name)
                }.onFailure {
                    setCheckedStudentName(false)
                    setStudentNumberMismatchError(true)
                    when (it) {
                        is team.aliens.dms.android.domain._legacy.exception.RemoteException.NotFound -> {

                        }

                        is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict -> {
                            postSideEffect(SignUpSideEffect.SetId.ConflictStudent)
                        }
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
                setConflictAccountIdError(it is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict)
            }
        }
    }

    private fun uploadProfile(): String {
        return runBlocking(Dispatchers.IO) {
            uploadFileUseCase(
                uploadFileInput = UploadFileInput(
                    file = stateFlow.value.profileImageUri!!.toFile(), // not-null asserted
                ),
            )
        }.fileUrl
    }

    private fun uploadImage() {
        if (stateFlow.value.profileImageUri == null) {
            postSideEffect(SignUpSideEffect.SetProfileImage.UploadImageNotSelected)
        }
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                uploadProfile()
            }.onSuccess {
                postSideEffect(SignUpSideEffect.SetProfileImage.UploadImageSuccess)
                setProfileImageUrl(it)
            }.onFailure {
                postSideEffect(SignUpSideEffect.SetProfileImage.UploadImageFailed)
            }
        }
    }
    
    private fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            with(stateFlow.value) {
                kotlin.runCatching {
                    signUpUseCase(
                        signUpInput = SignUpInput(
                            schoolVerificationCode = schoolCode,
                            schoolVerificationAnswer = schoolAnswer,
                            email = email,
                            emailVerificationCode = authCode,
                            grade = grade.toInt(),
                            classRoom = classRoom.toInt(),
                            number = number.toInt(),
                            accountId = accountId,
                            password = password,
                            profileImageUrl = profileImageUrl,
                        )
                    )
                }.onSuccess {
                    postSideEffect(SignUpSideEffect.Terms.SuccessSignUp)
                }.onFailure {
                    when (it) {
                        is team.aliens.dms.android.domain._legacy.exception.RemoteException.Unauthorized -> {
                            postSideEffect(SignUpSideEffect.Terms.EmailNotVerified)
                        }

                        is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict -> {
                            postSideEffect(SignUpSideEffect.Terms.AlreadyExistsStudent)
                        }
                    }
                }
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
        setSchoolCodeButtonEnabled(schoolCode.isNotEmpty() && !stateFlow.value.schoolCodeMismatchError)
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
        setSchoolAnswerButtonEnabled(schoolAnswer.isNotBlank())
    }
    
    private fun setEmail(
        email: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                email = email,
            )
        )
        setSendEmailButtonEnabled(email.isNotEmpty())
        setEmailFormatError(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email))
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
        setAuthCodeButtonEnabled(authCode.isNotEmpty())
    }

    private fun setGrade(
        grade: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                grade = grade,
            )
        )
        setIdButtonEnabled()
    }

    private fun setClassRoom(
        classRoom: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                classRoom = classRoom,
            )
        )
        setIdButtonEnabled()
    }

    private fun setNumber(
        number: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                number = number,
            )
        )
        setIdButtonEnabled()
    }

    private fun setAccountId(
        accountId: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                accountId = accountId,
            )
        )
        setIdButtonEnabled()
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

    private fun setCheckedStudentName(
        checkedStudentName: Boolean,
    ) {
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
        setPasswordFormatError(!Pattern.matches(passwordFormat, password))
    }

    private fun setPasswordRepeat(
        passwordRepeat: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                passwordRepeat = passwordRepeat,
            )
        )
        setPasswordMismatchError(stateFlow.value.password != passwordRepeat)
    }

    private fun checkPassword() {
        if (!stateFlow.value.passwordFormatError && !stateFlow.value.passwordMismatchError) {
            setPasswordButtonEnabled(true)
            postSideEffect(SignUpSideEffect.SetPassword.SuccessCheckPassword)
        }
    }
    
    private fun setProfileImageUri(
        profileImageUri: Uri?,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                profileImageUri = profileImageUri,
            )
        )
        setProfileImageEnabled(profileImageUri != null)
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

    private fun setProfileImageUrl(
        profileImageUrl: String,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                profileImageUrl = profileImageUrl,
            )
        )
    }

    private fun setCheckedPolicy(
        checkedPolicy: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                checkedPolicy = checkedPolicy,
            )
        )
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

    private fun setSchoolAnswerMismatchError(
        schoolAnswerMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolAnswerMismatchError = schoolAnswerMismatchError
            )
        )
        setSchoolAnswerButtonEnabled(!schoolAnswerMismatchError)
    }

    private fun setEmailFormatError(
        emailFormatError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                emailFormatError = emailFormatError,
            )
        )
        setSendEmailButtonEnabled(!emailFormatError)
    }

    private fun setAuthCodeMismatchError() {
        reduce(
            newState = stateFlow.value.copy(
                authCodeMismatchError = true,
            )
        )
        setAuthCodeButtonEnabled(false)
    }

    private fun setStudentNumberMismatchError(
        studentNumberMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                studentNumberMismatchError = studentNumberMismatchError,
            )
        )
        setIdButtonEnabled()
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

    private fun setPasswordFormatError(
        passwordFormatError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                passwordFormatError = passwordFormatError,
            )
        )
        setPasswordButtonEnabled(!passwordFormatError && !stateFlow.value.passwordMismatchError)
    }

    private fun setPasswordMismatchError(
        passwordMismatchError: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                passwordMismatchError = passwordMismatchError,
            )
        )
        setPasswordButtonEnabled(!stateFlow.value.passwordFormatError && !passwordMismatchError)
    }

    private fun setSchoolCodeButtonEnabled(
        schoolCodeButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolCodeButtonEnabled = schoolCodeButtonEnabled,
            )
        )
    }

    private fun setSchoolAnswerButtonEnabled(
        schoolAnswerButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                schoolAnswerButtonEnabled = schoolAnswerButtonEnabled,
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

    private fun setAuthCodeButtonEnabled(
        authCodeButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                authCodeButtonEnabled = authCodeButtonEnabled,
            )
        )
    }

    private fun setIdButtonEnabled() {
        with(stateFlow.value) {
            reduce(
                newState = copy(
                    idButtonEnabled = !studentNumberMismatchError && grade.isNotBlank() && classRoom.isNotBlank() && number.isNotBlank() && accountId.isNotBlank(),
                )
            )
        }
    }

    private fun setPasswordButtonEnabled(
        passwordButtonEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                passwordButtonEnabled = passwordButtonEnabled,
            )
        )
    }

    private fun setProfileImageEnabled(
        ProfileImageEnabled: Boolean,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                profileImageButtonEnabled = ProfileImageEnabled,
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
        class SetCheckedStudentName(val checkedStudentName: Boolean) : SetId()
        object ExamineStudentNumber : SetId()
        object CheckIdDuplication : SetId()
    }

    sealed class SetPassword : SignUpIntent() {
        class SetPassword(val password: String) : SignUpIntent.SetPassword()
        class SetPasswordRepeat(val passwordRepeat: String) : SignUpIntent.SetPassword()
        object CheckPassword : SignUpIntent.SetPassword()
    }

    sealed class SetProfileImage : SignUpIntent() {
        class SelectProfileImage(val profileImageUri: Uri) : SetProfileImage()
        object UploadImage : SetProfileImage()
    }

    sealed class Terms : SignUpIntent() {
        class SetCheckedPolicy(val checkedPolicy: Boolean) : Terms()
        object SignUp : Terms()
    }
}

data class SignUpState(
    val schoolCode: String,
    val schoolQuestion: String,
    val schoolAnswer: String,
    val email: String,
    val emailExpirationTime: String,
    val emailTimerWorked: Boolean,
    val authCode: String,
    val grade: String,
    val classRoom: String,
    val number: String,
    val accountId: String,
    val studentName: String,
    val checkedStudentName: Boolean,
    val password: String,
    val passwordRepeat: String,
    val profileImageUri: Uri?,
    val profileImageUrl: String,
    val schoolId: UUID?,
    val checkedPolicy: Boolean,
    val schoolCodeMismatchError: Boolean,
    val schoolAnswerMismatchError: Boolean,
    val emailFormatError: Boolean,
    val authCodeMismatchError: Boolean,
    val studentNumberMismatchError: Boolean,
    val conflictAccountIdError: Boolean,
    val passwordFormatError: Boolean,
    val passwordMismatchError: Boolean,
    val schoolCodeButtonEnabled: Boolean,
    val schoolAnswerButtonEnabled: Boolean,
    val sendEmailButtonEnabled: Boolean,
    val authCodeButtonEnabled: Boolean,
    val idButtonEnabled: Boolean,
    val passwordButtonEnabled: Boolean,
    val profileImageButtonEnabled: Boolean,
) : MviState {
    companion object {
        fun initial(): SignUpState {
            return SignUpState(
                schoolCode = "",
                schoolQuestion = "",
                schoolAnswer = "",
                email = "",
                emailExpirationTime = "",
                emailTimerWorked = false,
                authCode = "",
                grade = "",
                classRoom = "",
                number = "",
                accountId = "",
                studentName = "",
                checkedStudentName = false,
                password = "",
                passwordRepeat = "",
                profileImageUri = null,
                profileImageUrl = defaultProfileUrl,
                schoolId = null,
                checkedPolicy = false,
                schoolCodeMismatchError = false,
                schoolAnswerMismatchError = false,
                emailFormatError = false,
                authCodeMismatchError = false,
                studentNumberMismatchError = false,
                conflictAccountIdError = false,
                passwordFormatError = false,
                passwordMismatchError = false,
                schoolCodeButtonEnabled = false,
                schoolAnswerButtonEnabled = false,
                sendEmailButtonEnabled = false,
                authCodeButtonEnabled = false,
                idButtonEnabled = false,
                passwordButtonEnabled = false,
                profileImageButtonEnabled = false,
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
        object ConflictEmail : VerifyEmail()
    }

    sealed class SetId : SignUpSideEffect() {
        object NotFoundStudent: SetId()
        object ConflictStudent : SetId()
        object SuccessVerifyStudent : SetId()
    }

    sealed class SetPassword : SignUpSideEffect() {
        object SuccessCheckPassword : SetPassword()
    }

    sealed class SetProfileImage : SignUpSideEffect() {
        object UploadImageSuccess : SetProfileImage()
        object UploadImageFailed : SetProfileImage()
        object UploadImageNotSelected : SetProfileImage()
    }

    sealed class Terms : SignUpSideEffect() {
        object SuccessSignUp : Terms()
        object EmailNotVerified : Terms()
        object AlreadyExistsStudent : Terms()
    }
}*/
