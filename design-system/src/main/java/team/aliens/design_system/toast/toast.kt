package team.aliens.design_system.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.lang.ref.WeakReference
import kotlinx.coroutines.delay
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

class ToastState(
    var isVisible: Boolean,
    var toastType: ToastType? = null,
    var message: String? = null,
) {
    private suspend fun show(
        toastType: ToastType,
        message: String,
        duration: Long = 3000L,
    ) {
        this.toastType = toastType
        this.message = message
        this.isVisible = true

        delay(duration)

        this.isVisible = false
        this.toastType = null
        this.message = null
    }

    suspend fun showInformationToast(
        message: String
    ) {
        show(
            toastType = ToastType.INFORMATION,
            message = message,
        )
    }

    suspend fun showErrorToast(
        message: String,
    ) {
        show(
            toastType = ToastType.ERROR,
            message = message,
        )
    }

    suspend fun showSuccessToast(
        message: String,
    ) {
        show(
            toastType = ToastType.SUCCESS,
            message = message,
        )
    }
}

@Composable
fun rememberToastState(
    initialVisibility: Boolean = false,
): ToastState {
    return rememberSaveable {
        ToastState(
            isVisible = initialVisibility,
        )
    }
}

@Composable
fun DormToastLayout(
    toastState: ToastState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
        }
        AnimatedVisibility(
            modifier = modifier.fillMaxWidth(),
            visible = toastState.isVisible,
        ) {
            DormToast(
                contentColor = toastState.toastType!!.color,
                icon = toastState.toastType!!.icon,
                message = toastState.message!!,
            )
        }
    }
}

@Composable
private fun DormToast(
    contentColor: Color,
    icon: DormIcon,
    message: String,
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(
                    color = DormTheme.colors.primaryVariant,
                )
                .background(
                    color = DormTheme.colors.surface,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon.drawableId),
                contentDescription = null,
            )
            Spacer(Modifier.width(8.dp))
            Body3(
                text = message,
                color = contentColor,
            )
        }
    }
}

/*@Composable
fun DormToastHost(
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = Modifier.fillMaxSize(),
        hostState = hostState,
        snackbar = {it.actionLabel
            when () {
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
                    //throw IllegalArgumentException()
                }
            }
        }
    )
}*//*
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
}*/

enum class ToastType(
    val icon: DormIcon,
) {
    INFORMATION(DormIcon.Information), ERROR(DormIcon.Error), SUCCESS(DormIcon.Success), ;

    val color: Color
        @Composable get() = when (this) {
            INFORMATION -> DormTheme.colors.onBackground
            ERROR -> DormTheme.colors.error
            SUCCESS -> DormTheme.colors.onSecondary
        }
}
