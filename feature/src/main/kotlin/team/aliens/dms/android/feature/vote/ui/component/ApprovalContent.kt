
package team.aliens.dms.android.feature.vote.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.bottomPadding
import team.aliens.dms.android.core.designsystem.modifier.DmsShadowType
import team.aliens.dms.android.core.designsystem.modifier.dmsShadowModifier
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.voting.model.VotingItem

@Composable
internal fun ApprovalContent(
    title: String,
    options: List<VotingItem>,
    selectItem: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(80.dp)
                .bottomPadding(60.dp)
                .padding(horizontal = 24.dp),
            text = title,
            style = DmsTheme.typography.titleB,
            color = DmsTheme.colorScheme.surfaceContainer,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEachIndexed { index, option ->
                if (index == 0) {
                    ApprovalItem(
                        modifier = Modifier.weight(1f),
                        imageResource = R.drawable.ic_approve,
                        isSelected = option.id.toString() == selectItem,
                        clickColor = DmsTheme.colorScheme.onPrimary,
                        clickBorderColor = DmsTheme.colorScheme.onPrimaryContainer,
                        title = option.votingOptionName,
                        contentColor = DmsTheme.colorScheme.onPrimaryContainer,
                        clickContentColor = DmsTheme.colorScheme.inversePrimary,
                        onClick = { onSelect(option.id.toString()) },
                    )
                } else {
                    ApprovalItem(
                        modifier = Modifier.weight(1f),
                        imageResource = R.drawable.ic_oppose,
                        isSelected = option.id.toString() == selectItem,
                        clickColor = DmsTheme.colorScheme.onError,
                        clickBorderColor = DmsTheme.colorScheme.onErrorContainer,
                        title = option.votingOptionName,
                        contentColor = DmsTheme.colorScheme.onErrorContainer,
                        clickContentColor = DmsTheme.colorScheme.outline,
                        onClick = { onSelect(option.id.toString()) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ApprovalItem(
    @DrawableRes imageResource: Int,
    isSelected: Boolean,
    clickColor: Color,
    clickBorderColor: Color,
    contentColor: Color,
    clickContentColor: Color,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, borderColor, content) = if (isSelected) {
        Triple(clickColor, clickBorderColor, clickContentColor)
    } else {
        Triple(DmsTheme.colorScheme.surfaceTint, DmsTheme.colorScheme.surfaceVariant, contentColor)
    }
    Card(
        modifier = modifier
            .dmsShadowModifier(
                dmsShadowType = DmsShadowType.Light20,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(
            width = 2.dp,
            color = borderColor,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(imageResource),
                tint = content,
                contentDescription = null,
            )
            Text(
                text = title,
                style = DmsTheme.typography.bodyM,
                color = content,
            )
        }
    }
}
