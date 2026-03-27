package team.aliens.dms.android.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

object LocalResultStore {
    private val LocalStore: ProvidableCompositionLocal<ResultStore?> =
        compositionLocalOf { null }

    val current: ResultStore
        @Composable
        get() = LocalStore.current ?: error("No ResultStore has been provided")

    infix fun provides(
        store: ResultStore,
    ): ProvidedValue<ResultStore?> {
        return LocalStore.provides(store)
    }
}

@Composable
fun rememberResultStore(): ResultStore {
    return rememberSaveable(saver = resultStoreSaver()) {
        ResultStore()
    }
}

class ResultStore {

    val resultStateMap: MutableMap<String, MutableState<Any?>> = mutableMapOf()

    inline fun <reified T> getResultState(resultKey: String = T::class.toString()) =
        resultStateMap[resultKey]?.value as T

    inline fun <reified T> setResult(resultKey: String = T::class.toString(), result: T) {
        resultStateMap[resultKey] = mutableStateOf(result)
    }

    inline fun <reified T> removeResult(resultKey: String = T::class.toString()) {
        resultStateMap.remove(resultKey)
    }
}

private fun resultStoreSaver(): Saver<ResultStore, *> =
    Saver(
        save = { it.resultStateMap },
        restore = { ResultStore().apply { resultStateMap.putAll(it) } },
    )
