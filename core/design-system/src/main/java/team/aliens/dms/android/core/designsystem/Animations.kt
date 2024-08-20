package team.aliens.dms.android.core.designsystem

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

const val slideInDurationMillis = 100
const val slideOutDurationMillis = 80
const val slideOutDelayMillis = 60

fun slideInFromStart(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 10 } + fadeIn(
        animationSpec = tween(durationMillis = slideInDurationMillis),
    )
}

fun slideInFromEnd(): EnterTransition {
    return slideInHorizontally { initialOffset -> initialOffset / 10 * -1 } + fadeIn(
        animationSpec = tween(durationMillis = slideInDurationMillis),
    )
}

fun slideOutFromStart(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 10 } + fadeOut(
        animationSpec = tween(
            durationMillis = slideOutDurationMillis,
            delayMillis = slideOutDelayMillis,
        ),
    )
}

fun slideOutFromEnd(): ExitTransition {
    return slideOutHorizontally { initialOffset -> initialOffset / 10 * -1 } + fadeOut(
        animationSpec = tween(
            durationMillis = slideOutDurationMillis,
            delayMillis = slideOutDelayMillis,
        ),
    )
}
