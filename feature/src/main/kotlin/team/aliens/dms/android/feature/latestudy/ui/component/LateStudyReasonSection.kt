package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB

@Composable
fun LateStudyReasonSection (
    reason: String="",
    modifier: Modifier = Modifier,
) {
    val isEmpty = reason.isBlank()

    LateStudySectionCard(modifier = modifier) {
        Text(
            text = "사유",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyB,
        )
    }
}
