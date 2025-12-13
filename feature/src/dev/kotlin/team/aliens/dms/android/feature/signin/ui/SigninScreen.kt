package team.aliens.dms.android.feature.signin.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun SignInScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Login Screen (TODO)",
            textAlign = TextAlign.Center,
            color = DmsTheme.colorScheme.onSurface,
        )
    }
}
