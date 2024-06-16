package team.aliens.dms.android.core.widget.meal

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.widget.designsystem.WidgetTheme

@Composable
internal fun MealWidget() {
    val size = LocalSize.current
    Row(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(
                horizontal = 18.dp,
                vertical = 16.dp,
            )
            .background(DmsTheme.colorScheme.background),
    ) {
        Column(
            modifier = GlanceModifier.fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    provider = ImageProvider(DmsIcon.BlueBreakfast),
                    contentDescription = "d",
                )
                Spacer(GlanceModifier.width(4.dp))
                Text(
                    text = "아침",
                    style = TextStyle(
                        color = WidgetTheme.colors.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            Spacer(GlanceModifier.defaultWeight())
            Text(
                text = "kal",
                style = TextStyle(
                    color = WidgetTheme.colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            )
        }
        Spacer(GlanceModifier.defaultWeight())
        Text(
            text = "ddsfdsfsdfdss",
            style = TextStyle(
                color = WidgetTheme.colors.surfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )
        )
    }
}