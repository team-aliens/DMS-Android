package team.aliens.dms.android.core.designsystem.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.modifier.dmsDropShadow

@Composable
fun DmsSnackBar(
    modifier: Modifier = Modifier,
    snackBarType: DmsSnackBarType,
    message: String,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .dmsDropShadow(
                shape = CircleShape,
                color = DmsTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
                blur = 20.dp,
                offsetY = 0.dp,
            )
            .background(
                color = DmsTheme.colorScheme.surfaceTint,
                shape = CircleShape,
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Image(
            painter = painterResource(snackBarType.iconRes),
            contentDescription = null,
        )
        Text(
            text = message,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
    }
}

enum class DmsSnackBarType(
    @DrawableRes val iconRes: Int,
) {
    SUCCESS(
        iconRes = R.drawable.ic_success,
    ),
    ERROR(
        iconRes = R.drawable.ic_error,
    ),
    // TODO 노란색 구현
}
