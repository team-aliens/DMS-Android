package team.aliens.dms.android.domain.model._common

/**
 * An enum class of sex
 * @property MALE male
 * @property FEMALE female
 * @property ALL all sexes
 */
enum class Sex(
    val koreanValue: String,
) {
    MALE("남"), FEMALE("여"), ALL("남녀"),
    ;
}
