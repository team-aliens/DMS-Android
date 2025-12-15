package team.aliens.dms.android.core.designsystem.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun DmsSymbol(modifier: Modifier = Modifier) {
    val symbol = if (isSystemInDarkTheme()) {
        DmsIcon.SymbolDark
    } else {
        DmsIcon.SymbolLight
    }

    Image(
        modifier = modifier,
        painter = painterResource(symbol),
        contentDescription = "symbol",
    )
}
