package team.aliens.dms.android.core.designsystem.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.designsystem.topPadding

@Composable
fun DmsSymbolContent(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
) {
    val symbol = if (isSystemInDarkTheme()) {
        DmsIcon.SymbolDark
    } else {
        DmsIcon.SymbolLight
    }

    Column(
        modifier = Modifier
            .horizontalPadding(24.dp),
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(symbol),
            contentDescription = "symbol",
        )
        Text(
            modifier = Modifier.topPadding(20.dp),
            text = title,
            style = DmsTheme.typography.titleB,
            color = DmsTheme.colorScheme.onTertiaryContainer,
        )
        Text(
            modifier = Modifier.topPadding(12.dp),
            text = description,
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
    }
}
