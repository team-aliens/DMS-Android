package team.aliens.dms_android.domain.model._common

import team.aliens.dms_android.domain.model._common.EmailVerificationType.PASSWORD
import team.aliens.dms_android.domain.model._common.EmailVerificationType.SIGNUP

/**
 * An enum class of email verification type
 * @property SIGNUP a value of sign up type
 * @property PASSWORD a value of password type
 */
enum class EmailVerificationType {
    SIGNUP,
    PASSWORD,
    ;
}
