package team.aliens.dms.android.shared.exception.util

import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <T> runCatchingCancellable(
    crossinline block: suspend () -> T,
): Result<T> =
    try {
        Result.success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: Exception) {
        Result.failure(exception)
    }
