package team.aliens.dms.android.shared.validator

abstract class Validator<T : Any> {
    abstract val regex: Regex

    abstract fun validate(value: T): Boolean
}
