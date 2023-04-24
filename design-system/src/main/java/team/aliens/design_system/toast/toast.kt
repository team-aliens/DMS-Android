package team.aliens.design_system.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.lang.ref.WeakReference
import kotlinx.coroutines.delay
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.extension.Space
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
fun DormToast(
    message: String,
    toastType: ToastType,
    isShowToast: MutableState<Boolean>,
    duration: Long = 3000,
) {
    LaunchedEffect(isShowToast.value) {
        delay(duration)
        isShowToast.value = false
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Space(14.dp)
        AnimatedVisibility(
            visible = isShowToast.value,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = 48.dp,
                    )
                    .dormShadow(
                        color = DormTheme.colors.primaryVariant,
                    )
                    .clip(
                        shape = RoundedCornerShape(4.dp),
                    )
                    .background(
                        color = DormTheme.colors.onError,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Space(space = 14.dp)
                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(toastType.drawable),
                    contentDescription = null,
                )
                Space(space = 8.dp)
                Body3(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = message,
                    color = toastType.textColor,
                )
            }
        }
    }
}

enum class ToastType(
    val textColor: Color,
    @DrawableRes val drawable: Int,
) {
    INFO(
        textColor = DormColor.Gray900,
        drawable = DormIcon.Information.drawableId,
    ),

    ERROR(
        textColor = DormColor.Error,
        drawable = DormIcon.Error.drawableId,
    ),

    SUCCESS(
        textColor = DormColor.DormPrimary,
        drawable = DormIcon.Success.drawableId,
    )
}
