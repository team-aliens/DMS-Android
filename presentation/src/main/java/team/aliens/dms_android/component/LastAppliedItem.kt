package team.aliens.dms_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.ButtonText
import team.aliens.presentation.R

@Composable
fun LastAppliedItem(
    text: String,
    backgroundColor: Color = DormTheme.colors.secondary,
    textColor: Color = DormTheme.colors.primary,
) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .padding(
                horizontal = 12.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        ButtonText(
            text = text.ifBlank { stringResource(id = R.string.CompleteApplication) },
            color = textColor,
        )
    }
}