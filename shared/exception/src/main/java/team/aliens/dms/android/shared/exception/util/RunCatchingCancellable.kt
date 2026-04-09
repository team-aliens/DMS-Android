package team.aliens.dms.android.shared.exception.util

import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> runCatchingCancellable(crossinline block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
