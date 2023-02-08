package team.aliens.design_system.animate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

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