package team.aliens.dms.android.core.designsystem.card

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.endPadding
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.util.clickable

@Composable
fun DmsApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    period: String? = null,
    appliedTitle: String? = null,
    @DrawableRes iconRes: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            DmsTheme.colorScheme.onPrimaryContainer
        } else {
            DmsTheme.colorScheme.surfaceTint
        },
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(DmsTheme.colorScheme.surfaceTint)
            .clickable(onClick = onClick)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(iconRes),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = DmsTheme.typography.bodyB,
                color = DmsTheme.colorScheme.inverseOnSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (description == null && appliedTitle != null) {
                AppliedTitleText(
                    modifier = Modifier.endPadding(16.dp),
                    appliedTitle = appliedTitle,
                )
            }
            Icon(
                painter = painterResource(DmsIcon.Forward),
                tint = DmsTheme.colorScheme.scrim,
                contentDescription = null,
            )
        }
        period?.let {
            Text(
                text = period,
                style = DmsTheme.typography.labelM,
                color = DmsTheme.colorScheme.onPrimaryContainer,
            )
        }
        description?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = description,
                    style = DmsTheme.typography.labelM,
                    color = DmsTheme.colorScheme.inverseSurface,
                )
                Spacer(modifier = Modifier.weight(1f))
                appliedTitle?.let {
                    AppliedTitleText(appliedTitle = appliedTitle)
                }
            }
        }
    }
}

@Composable
private fun AppliedTitleText(
    modifier: Modifier = Modifier,
    appliedTitle: String,
) {
    Text(
        modifier = modifier
            .background(
                color = DmsTheme.colorScheme.primary,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 22.dp, vertical = 8.dp),
        text = appliedTitle,
        style = DmsTheme.typography.labelB,
        color = DmsTheme.colorScheme.onPrimaryContainer,
    )
}
