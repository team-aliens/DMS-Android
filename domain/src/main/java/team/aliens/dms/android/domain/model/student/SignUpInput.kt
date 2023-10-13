package team.aliens.dms.android.domain.model.student

/**
 * A request of sign up
 * @property schoolVerificationCode a verification code of user's school
 * @property schoolVerificationAnswer an answer of user's school question
 * @property email an email of user
 * @property emailVerificationCode a verification code, sent to user's email
 * @property grade a grade of user
 * @property classRoom a class of user
 * @property number a number of user
 * @property accountId an id of user
 * @property password a password of user
 * @property profileImageUrl an url of profile image, can be null
 */
data class SignUpInput(
    val schoolVerificationCode: String,
    val schoolVerificationAnswer: String,
    val email: String,
    val emailVerificationCode: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val profileImageUrl: String? = null,
)
