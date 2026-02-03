package team.aliens.dms.android.feature.main.home.model

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon

@Composable
fun DmsPointContent(
    modifier: Modifier = Modifier,
    bonusPoint: Int,
    minusPoint: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                color = DmsTheme.colorScheme.surfaceTint,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(24.dp),
    ) {
        Text(
            text = "상벌점",
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseSurface,
        )
        PointItem(
            modifier = Modifier.padding(top = 16.dp),
            textColor = DmsTheme.colorScheme.onTertiaryContainer,
            buttonColor = DmsTheme.colorScheme.onSurface,
            icon = DmsIcon.Equal,
            title = "총점",
            point = bonusPoint - minusPoint,
        )
        PointItem(
            modifier = Modifier.padding(top = 12.dp),
            textColor = DmsTheme.colorScheme.onPrimaryContainer,
            buttonColor = DmsTheme.colorScheme.primary,
            icon = DmsIcon.Plus,
            title = "상점",
            point = bonusPoint,
        )
        PointItem(
            modifier = Modifier.padding(top = 12.dp),
            textColor = DmsTheme.colorScheme.onErrorContainer,
            buttonColor = DmsTheme.colorScheme.error,
            icon = DmsIcon.Minus,
            title = "벌점",
            point = minusPoint,
        )
    }
}

@Composable
private fun PointItem(
    modifier: Modifier = Modifier,
    textColor: Color,
    buttonColor: Color,
    @DrawableRes icon: Int,
    title: String,
    point: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = DmsTheme.typography.bodyB,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .background(color = buttonColor, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 22.dp, vertical = 8.dp),
            text = "${point}점",
            style = DmsTheme.typography.bodyB,
            color = textColor,
        )
    }
}
