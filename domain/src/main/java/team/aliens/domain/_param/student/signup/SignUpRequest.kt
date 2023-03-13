package team.aliens.domain._param.student.signup

/**
 * @author junsuPark
 * A request of sign up
 * [schoolCode] a verification code of user's school
 * [schoolAnswer] an answer of user's school question
 * [email] an email of user
 * [authCode] a verification code, sent to user's email
 * [grade] a grade of user
 * [classRoom] a class of user
 * [number] a number of user
 * [accountId] an id of user
 * [password] a password of user
 * [profileImageUrl] an url of profile image, can be null
 */
data class SignUpRequest(
    val schoolCode: String,
    val schoolAnswer: String,
    val email: String,
    val authCode: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val profileImageUrl: String? = null,
)
