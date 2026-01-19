package team.aliens.dms.android.shared.exception.util

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> suspendRunCatching(block: () -> T): Result<T> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.failure(e)
}
