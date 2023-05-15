package team.aliens.design_system.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.lang.ref.WeakReference
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3

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

@Composable
fun DormToastHost(
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = Modifier.fillMaxSize(),
        hostState = hostState,
        snackbar = {
            when (ToastType.valueOf(it.actionLabel.toString())) {
                ToastType.INFORMATION -> {
                    DormInfoToast(
                        message = it.message,
                    )
                }

                ToastType.ERROR -> {
                    DormErrorToast(
                        message = it.message,
                    )
                }

                ToastType.SUCCESS -> {
                    DormSuccessToast(
                        message = it.message,
                    )
                }

                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    )
}

@Composable
private fun DormToast(
    message: String,
    @DrawableRes drawable: Int,
    messageColor: Color,
) {
    Box(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 14.dp,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(
                    color = DormTheme.colors.primaryVariant,
                    alpha = 0.3f,
                )
                .background(
                    color = DormTheme.colors.surface,
                    shape = RoundedCornerShape(
                        size = 4.dp,
                    )
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = drawable),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Body3(
                text = message,
                color = messageColor,
            )
        }
    }
}

@Composable
fun DormInfoToast(
    message: String,
) {
    DormToast(
        message = message,
        drawable = DormIcon.Information.drawableId,
        messageColor = DormTheme.colors.onSurface,
    )
}

@Composable
fun DormErrorToast(
    message: String,
) {
    DormToast(
        message = message,
        drawable = DormIcon.Error.drawableId,
        messageColor = DormTheme.colors.error,
    )
}

@Composable
fun DormSuccessToast(
    message: String,
) {
    DormToast(
        message = message,
        drawable = DormIcon.Success.drawableId,
        messageColor = DormTheme.colors.primary,
    )
}

enum class ToastType {
    INFORMATION, ERROR, SUCCESS
}
