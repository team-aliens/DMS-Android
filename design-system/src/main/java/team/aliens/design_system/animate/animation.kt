package team.aliens.design_system.animate

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun slideInFromStart(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 10 } + fadeIn(
        animationSpec = tween(durationMillis = 100),
    )
}

fun slideInFromEnd(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 10 * -1 } + fadeIn(
        animationSpec = tween(durationMillis = 100),
    )
}

fun slideOutFromStart(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 10 } + fadeOut(
        animationSpec = tween(
            durationMillis = 80,
            delayMillis = 60,
        )
    )
}

fun slideOutFromEnd(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 10 * -1 } + fadeOut(
        animationSpec = tween(
            durationMillis = 80,
            delayMillis = 60,
        )
    )
}
