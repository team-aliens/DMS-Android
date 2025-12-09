package team.aliens.dms.android.feature.onboarding.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.ButtonColor
import team.aliens.dms.android.core.designsystem.ButtonType
import team.aliens.dms.android.core.designsystem.DmsButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.headlineB

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
