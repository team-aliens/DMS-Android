package team.aliens.dms_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.ButtonText
import team.aliens.presentation.R

@Composable
fun LastAppliedItem(
    lastApplied: String = ""
){
    Box(
        modifier = Modifier
            .size(
                width = 92.dp,
                height = 34.dp,
            )
            .background(
                color = DormColor.Lighten200,
                shape = RoundedCornerShape(100)
            ),
        contentAlignment = Alignment.Center,
    ) {
        ButtonText(
            text = lastApplied.ifBlank { stringResource(id = R.string.CompleteApplication) },
            color = DormColor.DormPrimary,
        )
    }
}