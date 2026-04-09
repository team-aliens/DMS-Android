package team.aliens.dms.android.core.datastore.util

import team.aliens.dms.android.core.datastore.exception.TransformFailureException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> transform(
    onSuccess: (T) -> Unit = {},
    onFailure: (Throwable) -> Unit = { throw TransformFailureException() },
    crossinline block: suspend () -> T,
) = try {
    block().also(onSuccess)
} catch (exception: CancellationException) {
    throw exception
} catch (throwable: Throwable) {
    onFailure(throwable)
    throw throwable
}
