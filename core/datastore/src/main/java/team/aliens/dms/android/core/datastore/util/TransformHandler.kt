package team.aliens.dms.android.core.datastore.util

import team.aliens.dms.android.core.datastore.exception.TransformFailureException

suspend inline fun <reified T> transform(
    onSuccess: (T) -> Unit = {},
    onFailure: (Throwable) -> Unit = { throw TransformFailureException() },
    crossinline block: suspend () -> T,
) = runCatching { block() }
    .onSuccess(onSuccess)
    .onFailure(onFailure)
    .getOrThrow()
