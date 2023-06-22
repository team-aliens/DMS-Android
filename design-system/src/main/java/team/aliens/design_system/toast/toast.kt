package team.aliens.design_system.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.lang.ref.WeakReference
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import kotlin.coroutines.resume

@Deprecated("Legacy")
class ToastWrapper(context: Context) {
    private val contextWrapper = WeakReference(context)
    private val _context get() = contextWrapper.get()!!

    private val toastInstance = Toast.makeText(_context, "", Toast.LENGTH_SHORT)

    operator fun invoke(
        message: Any,
        length: Int = Toast.LENGTH_SHORT,
    ) = toastInstance.run {
        setText(message.toString())
        duration = length
        show()
    }
}

@Deprecated("Legacy")
@Composable
fun rememberToast(): ToastWrapper {
    val context = LocalContext.current.applicationContext
    return remember(
        key1 = context,
    ) {
        ToastWrapper(
            context = context,
        )
    }
}

val LocalToast = staticCompositionLocalOf<ToastState> {
    error("LocalToast not Implemented")
}

interface ToastData {
    val toastType: ToastType
    val message: String
    val duration: Long

    fun dismiss()
}

enum class ToastResult {
    DISMISSED, ;
}

class ToastState {
    private val mutex = Mutex()
    var currentToastData by mutableStateOf<ToastData?>(null)
        private set

    private suspend fun show(
        toastType: ToastType,
        message: String,
        duration: Long,
    ): ToastResult = mutex.withLock {
        try {
            return suspendCancellableCoroutine { continuation ->
                currentToastData = ToastDataImpl(
                    toastType = toastType,
                    message = message,
                    duration = duration,
                    continuation = continuation,
                )
            }
        } finally {
            delay(1000L)
            currentToastData = null
        }
    }

    suspend fun showInformationToast(
        message: String,
        duration: Long = 3000L,
    ) {
        show(
            toastType = ToastType.INFORMATION,
            message = message,
            duration = duration,
        )
    }

    suspend fun showErrorToast(
        message: String,
        duration: Long = 3000L,
    ) {
        show(
            toastType = ToastType.ERROR,
            message = message,
            duration = duration,
        )
    }

    suspend fun showSuccessToast(
        message: String,
        duration: Long = 2000L,
    ) {
        show(
            toastType = ToastType.SUCCESS,
            message = message,
            duration = duration,
        )
    }

    private class ToastDataImpl(
        override val toastType: ToastType,
        override val message: String,
        override val duration: Long,
        private val continuation: CancellableContinuation<ToastResult>,
    ) : ToastData {
        override fun dismiss() {
            if (continuation.isActive) continuation.resume(ToastResult.DISMISSED)
        }
    }
}

@Composable
fun DormToastHost(
    toastState: ToastState,
) {
    val currentToastData = toastState.currentToastData
    LaunchedEffect(currentToastData) {
        if (currentToastData != null) {
            delay(currentToastData.duration)
            currentToastData.dismiss()
        }
    }
    DormToast(currentToastData)
}

@Composable
fun rememberToastState(): ToastState {
    val state = ToastState()
    return remember(state.currentToastData) {
        ToastState()
    }
}

@Composable
fun DormToastLayout(
    modifier: Modifier = Modifier,
    toastState: ToastState,
    toastHost: @Composable (ToastState) -> Unit = { DormToastHost(it) },
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalToast provides toastState) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                content()
            }
            toastHost(toastState)
        }
    }
}

@Composable
private fun DormToast(
    toastData: ToastData?,
) {
    var visible by remember(toastData) { mutableStateOf(toastData != null) }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        toastData?.run {
            LaunchedEffect(toastData) {
                delay(toastData.duration)
                visible = false
            }
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 14.dp,
                    )
                    .fillMaxWidth()
                    .dormShadow(
                        color = DormTheme.colors.primaryVariant,
                    )
                    .background(
                        color = DormTheme.colors.surface,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 14.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val toastType = toastData.toastType
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(toastType.icon.drawableId),
                    contentDescription = null,
                    tint = toastType.color,
                )
                Spacer(Modifier.width(8.dp))
                Body3(
                    text = toastData.message,
                    color = toastType.color,
                )
            }
        }
    }
}

enum class ToastType(
    val icon: DormIcon,
) {
    INFORMATION(DormIcon.Information), ERROR(DormIcon.Warning), SUCCESS(DormIcon.Check), ;

    val color: Color
        @Composable get() = when (this) {
            INFORMATION -> DormTheme.colors.onBackground
            ERROR -> DormTheme.colors.error
            SUCCESS -> DormTheme.colors.onSecondary
        }
}
