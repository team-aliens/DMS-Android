package team.aliens.dms_android.feature.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
private fun getActivity() = LocalContext.current as ComponentActivity

@Composable
internal inline fun <reified VM : ViewModel> composableActivityViewModel(): VM = viewModel(
    modelClass = VM::class.java,
    viewModelStoreOwner = getActivity(),
    key = null,
    factory = null,
)