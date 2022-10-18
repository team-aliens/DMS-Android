package com.example.design_system.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner

class ToastMaker(context: Context) : Toast(context) {
    @Composable
    fun MakeTest(
        message: String,
        duration: Int = LENGTH_LONG,
        stateToast: Int,
        textColor: Color,
    ) {
        val context = LocalContext.current

        val views = ComposeView(context)
        views.setContent {
            ToastView(
                messageTxt = message,
                stateToast = stateToast,
                textColor = textColor,
            )
        }

        ViewTreeLifecycleOwner.set(views, LocalLifecycleOwner.current)
        ViewTreeViewModelStoreOwner.set(views, LocalViewModelStoreOwner.current)
        ViewTreeSavedStateRegistryOwner.set(views, LocalSavedStateRegistryOwner.current)

        this.duration = duration
        this.view = views
    }
}
