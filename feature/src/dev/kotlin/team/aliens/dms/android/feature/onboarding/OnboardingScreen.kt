package team.aliens.dms.android.feature.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.ButtonColor
import team.aliens.dms.android.core.designsystem.ButtonType
import team.aliens.dms.android.core.designsystem.DmsButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.headlineB

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

@Composable
internal fun CompleteContent(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit,
) {
    var step by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        step = 1
        delay(500)
        step = 2
        delay(500)
        step = 3
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxHeight(0.6f),
            visible = step >= 1,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn(
                animationSpec = tween(
                    durationMillis = 600,
                ),
            ),
        ) {
            // TODO :: 디자인 맞게 그림자 넣어줘야함
            Image(
                painter = painterResource(R.drawable.img_3d_logo),
                contentDescription = "3d 로고 이미지",
            )
        }
        AnimatedVisibility(
            visible = step >= 2,
        ) {
            val startColor by rememberInfiniteTransition().animateColor(
                initialValue = Color(
                    0xFFC176D0,
                ),
                targetValue = Color(0xFF62A4FF),
                animationSpec = infiniteRepeatable(animation = tween(durationMillis = 600)),
            )
            val endColor by rememberInfiniteTransition().animateColor(
                initialValue = Color(0xFF62A4FF),
                targetValue = Color(0xFFC176D0),
                animationSpec = infiniteRepeatable(animation = tween(durationMillis = 600)),
            )
            val brush = Brush.linearGradient(
                colors = listOf(startColor, endColor),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "최고의",
                    style = DmsTheme.typography.headlineB,
                    color = DmsTheme.colorScheme.backgroundVariant,
                )
                Text(
                    text = "기숙사 관리 시스템.",
                    style = DmsTheme.typography.headlineB.copy(brush = brush),
                    color = DmsTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = step >= 3,
            modifier = Modifier.padding(16.dp),
        ) {
            DmsButton(
                text = "시작하기",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                onClick = onCompleteClick,
                enabled = true,
            )
        }
    }
}

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
