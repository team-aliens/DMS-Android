package team.aliens.design_system.utils

internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this
