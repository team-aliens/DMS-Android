package team.aliens.design_system.animate

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun slideInToStart(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 2 } + fadeIn(
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 100,
        ),
    )
}

fun slideInToEnd(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 2 * -1 } + fadeIn(
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 100,
        ),
    )
}

fun slideOutToStart(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 2 } + fadeOut(
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 100,
        )
    )
}

fun slideOutToEnd(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 2 * -1 } + fadeOut(
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 100,
        )
    )
}
