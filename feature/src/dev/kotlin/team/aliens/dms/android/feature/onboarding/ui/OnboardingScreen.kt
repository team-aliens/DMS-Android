package team.aliens.dms.android.feature.onboarding.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.onboarding.component.CompleteContent
import team.aliens.dms.android.feature.onboarding.component.IntroContent

@Composable
internal fun OnboardingScreen(
    onComplete: () -> Unit,
) {
    var isLastStep by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = isLastStep,
            contentAlignment = Alignment.Center,
        ) { isLast ->
            if (isLast) {
                CompleteContent(onCompleteClick = onComplete)
            } else {
                IntroContent(
                    onAnimatedEnd = { isLastStep = true },
                )
            }
        }
    }
}
