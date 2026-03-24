package team.aliens.dms.android.feature.main.mypage.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM

@Composable
internal fun PhraseContent(
    phrase: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colorScheme.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp, vertical = 26.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = phrase,
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
    }
}
