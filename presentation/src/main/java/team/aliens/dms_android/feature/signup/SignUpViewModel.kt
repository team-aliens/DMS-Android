package team.aliens.dms_android.feature.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSingleEvent
import team.aliens.dms_android.base.MviViewState
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
    // 학교 인증 코드
    private val examineSchoolVerificationCodeUseCase: ExamineSchoolVerificationCodeUseCase,

    // 학교 질문 & 답변
    private val fetchSchoolVerificationQuestionUseCase: FetchSchoolVerificationQuestionUseCase,
    private val examineSchoolVerificationQuestionUseCase: ExamineSchoolVerificationQuestionUseCase,

    // 이메일 보내기
    private val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,

    // 이메일 인증코드 인증
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
            is SignUpIntent.SetSchoolVerificationCode -> {
                setSchoolVerificationCode(schoolCode = intent.schoolCode)
            }

            is SignUpIntent.SetSchoolVerificationAnswer -> {
                setSchoolVerificationAnswer(schoolVerificationAnswer = intent.schoolVerificationAnswer)
            }

            is SignUpIntent.SetEmail -> {
                setEmail(email = intent.email)
            }

            is SignUpIntent.SetEmailVerificationCode -> {
                setEmailVerificationCode(emailVerificationCode = intent.emailVerificationCode)
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



    private fun setSchoolVerificationCode(
        schoolCode: String,
    ){
        setState(
            newState = uiState.value.copy(
                schoolVerificationCode = schoolCode,
            )
        )
    }

    private fun setSchoolVerificationAnswer(
        schoolVerificationAnswer: String,
    ){
        setState(
            newState = uiState.value.copy(
                schoolVerificationAnswer = schoolVerificationAnswer,
            )
        )
    }

    private fun setEmail(
        email: String,
    ){
        setState(
            newState = uiState.value.copy(
                email = email,
            )
        )
    }

    private fun setEmailVerificationCode(
        emailVerificationCode: String,
    ){
        setState(
            newState = uiState.value.copy(
                emailVerificationCode = emailVerificationCode,
            )
        )
    }

    private fun setGrade(
        grade: Int,
    ){
        setState(
            newState = uiState.value.copy(
                grade = grade,
            )
        )
    }

    private fun setClassRoom(
        classRoom: Int,
    ){
        setState(
            newState = uiState.value.copy(
                classRoom = classRoom,
            )
        )
    }

    private fun setNumber(
        number: Int,
    ){
        setState(
            newState = uiState.value.copy(
                number = number,
            )
        )
    }

    private fun setAccountId(
        accountId: String,
    ){
        setState(
            newState = uiState.value.copy(
                accountId = accountId,
            )
        )
    }

    private fun setPassword(
        password: String,
    ){
        setState(
            newState = uiState.value.copy(
                password = password,
            )
        )
    }

    private fun setPasswordRepeat(
        passwordRepeat: String,
    ){
        setState(
            newState = uiState.value.copy(
                passwordRepeat = passwordRepeat,
            )
        )
    }

    private fun setProfileImageUrl(
        profileImageUrl: String,
    ){
        setState(
            newState = uiState.value.copy(
                profileImageUrl = profileImageUrl,
            )
        )
    }
}

sealed class SignUpIntent : MviIntent {
    class SetSchoolVerificationCode(val schoolCode: String) : SignUpIntent()
    class SetSchoolVerificationAnswer(val schoolVerificationAnswer: String) : SignUpIntent()
    class SetEmail(val email: String) : SignUpIntent()
    class SetEmailVerificationCode(val emailVerificationCode: String) : SignUpIntent()
    class SetGrade(val grade: Int) : SignUpIntent()
    class SetClassRoom(val classRoom: Int) : SignUpIntent()
    class SetNumber(val number: Int) : SignUpIntent()
    class SetAccountId(val accountId: String) : SignUpIntent()
    class SetPassword(val password: String) : SignUpIntent()
    class SetPasswordRepeat(val passwordRepeat: String) : SignUpIntent()
}

data class SignUpState(
    val schoolVerificationCode: String,
    val question: String,
    val schoolVerificationAnswer: String,
    val email: String,
    val emailVerificationCode: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val passwordRepeat: String,
    val profileImageUrl: String,
    val schoolId: UUID?,
) : MviViewState {
    companion object {
        fun initial(): SignUpState {
            return SignUpState(
                schoolVerificationCode = "",
                question = "",
                schoolVerificationAnswer = "",
                email = "",
                emailVerificationCode = "",
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

sealed class SignUpSideEffect : MviSingleEvent {
    object MoveToNextScreen : SignUpSideEffect()
    object NotCorrectSchoolVerificationCode : SignUpSideEffect()
    object NotCorrectSchoolVerificationAnswer : SignUpSideEffect()
    object InvalidFormatEmail : SignUpSideEffect()
    object NotCorrectEmailVerificationCode : SignUpSideEffect()
    object NotExistsStudentNumber : SignUpSideEffect()
    object DuplicatedStudent : SignUpSideEffect()
    object MisMatchPasswordRepeat : SignUpSideEffect()
    object SuccessSetProfileImage: SignUpSideEffect()
    object SuccessSignUp: SignUpSideEffect()
}

