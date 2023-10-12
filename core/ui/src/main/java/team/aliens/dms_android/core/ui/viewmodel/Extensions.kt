package team.aliens.dms_android.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun <R> ViewModel.launchOnViewModelScope(
    context: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> R,
) = this.viewModelScope.launch(context = context) { block() }

fun <R> ViewModel.asyncOnViewModelScope(
    context: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> R,
) = this.viewModelScope.async(context = context) { block() }

fun <R> ViewModel.runBlockingOnViewModelScope(
    context: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> R,
) = runBlocking(context = context) { block() }
