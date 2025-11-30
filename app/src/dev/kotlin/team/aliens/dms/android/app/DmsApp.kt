package team.aliens.dms.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.DmsTheme

@Serializable
data object ScreenA : NavKey

@Serializable
data object ScreenB : NavKey

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
//    displayFeatures: List<DisplayFeature>,
//    isJwtAvailable: StateFlow<Boolean>,
//    appState: DmsAppState = rememberDmsAppState(
//        isJwtAvailable = isJwtAvailable,
//    ),
) {
    val backStack = rememberNavBackStack(ScreenA)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ScreenA> {
                    Button(onClick = { backStack.add(ScreenB) }) {
                        Text("Go to Screen B")
                    }
                }
                entry<ScreenB> {
                    Text(
                        text = "Screen B",
                        color = DmsTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    }
}
