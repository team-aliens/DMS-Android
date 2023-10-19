package team.aliens.dms.android.core.designsystem.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import team.aliens.dms.android.core.designsystem.annotation.DormDeprecated

@DormDeprecated
@Composable
fun <T> T.HorizontalAnimationBox(
    enter: EnterTransition = expandHorizontally() + fadeIn(),
    exit: ExitTransition = fadeOut() + shrinkHorizontally(),
    content: @Composable T.() -> Unit,
) {
    val state = remember {
        MutableTransitionState(false).apply {
            this.targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}

@DormDeprecated
@Composable
fun <T> T.VerticalAnimationBox(
    enter: EnterTransition = expandHorizontally() + fadeIn(),
    exit: ExitTransition = fadeOut() + shrinkHorizontally(),
    content: @Composable T.() -> Unit,
) {
    val state = remember {
        MutableTransitionState(false).apply {
            this.targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}