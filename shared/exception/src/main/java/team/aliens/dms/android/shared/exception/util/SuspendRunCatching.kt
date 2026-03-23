package team.aliens.dms.android.shared.exception.util

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> suspendRunCatching(block: () -> T): Result<T> =
    runCatching(block).onFailure {
        if (it is CancellationException) throw it
    }
