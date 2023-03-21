package team.aliens.domain._model._common

import team.aliens.domain._model._common.EmailVerificationType.PASSWORD
import team.aliens.domain._model._common.EmailVerificationType.SIGNUP

/**
 * @author junsuPark
 * An enum class of email verification type
 * @property SIGNUP a value of sign up type
 * @property PASSWORD a value of password type
 */
enum class EmailVerificationType {
    SIGNUP,
    PASSWORD,
    ;
}
