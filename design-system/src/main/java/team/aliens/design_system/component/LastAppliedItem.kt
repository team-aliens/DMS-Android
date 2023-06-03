package team.aliens.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.ButtonText

val DefaultAppliedTagSize = Modifier.defaultMinSize(
    minWidth = 60.dp,
    minHeight = 30.dp,
)

val StudyRoomAppliedTagSize = Modifier.defaultMinSize(
    minWidth = 50.dp,
    minHeight = 26.dp,
)

@Composable
fun LastAppliedItem(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = DormTheme.colors.secondary,
    textColor: Color = DormTheme.colors.primary,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .padding(
                vertical = 4.dp,
                horizontal = 12.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        ButtonText(
            text = text,
            color = textColor,
        )
    }
}