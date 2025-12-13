package team.aliens.dms.android.feature.onboarding.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.headlineB

@Composable
internal fun IntroContent(
    modifier: Modifier = Modifier,
    onAnimatedEnd: () -> Unit,
) {
    var step by remember { mutableStateOf(0) }
    var isPointVisible by remember { mutableStateOf(false) }
    val pointColor by animateColorAsState(
        targetValue = if (isPointVisible) {
            DmsTheme.colorScheme.primary
        } else {
            DmsTheme.colorScheme.backgroundVariant
        },
    )

    LaunchedEffect(Unit) {
        delay(300)
        step = 1
        delay(800)
        isPointVisible = true
        delay(1000)
        step = 0
        delay(300)
        step = 2
        delay(1300)
        step = 0
        delay(300)
        step = 3
        delay(1200)
        onAnimatedEnd()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = step == 1,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
        ) {
            Text(
                buildAnnotatedString {
                    append("새로운 ")
                    withStyle(
                        style = SpanStyle(color = pointColor),
                    ) {
                        append("DMS")
                    }
                    append("에\n오신걸 환영해요!")
                },
                style = DmsTheme.typography.headlineB,
                color = DmsTheme.colorScheme.backgroundVariant,
                textAlign = TextAlign.Center,
            )
        }
        AnimatedVisibility(
            visible = step == 2,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + expandVertically(
                expandFrom = Alignment.Top,
            ),
            exit = fadeOut() + slideOutVertically(),
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = pointColor),
                    ) {
                        append("DMS")
                    }
                    append("가\n새롭게 바뀌었어요!")
                },
                style = DmsTheme.typography.headlineB,
                color = DmsTheme.colorScheme.backgroundVariant,
                textAlign = TextAlign.Center,
            )
        }
        AnimatedVisibility(
            visible = step == 3,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + expandVertically(
                expandFrom = Alignment.Top,
            ),
        ) {
            Text(
                text = "최고의\n기숙사 관리 시스템.",
                style = DmsTheme.typography.headlineB,
                color = DmsTheme.colorScheme.backgroundVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}
