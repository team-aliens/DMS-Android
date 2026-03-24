package team.aliens.dms.android.feature.main.mypage.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB

@Composable
internal fun PhraseCard(
    phrase: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.primary,
        ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            text = phrase,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.surfaceBright,
        )
    }
}
