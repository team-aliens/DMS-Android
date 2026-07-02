package team.aliens.dms.android.feature.dmsai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
fun DmsAiRoute() {
    DmsAiScreen()
}

@Composable
private fun DmsAiScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
    )
}
