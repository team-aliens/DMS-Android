package team.aliens.dms.android.feature.main.home.model

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.topPadding

@Composable
internal fun MealContent(
    modifier: Modifier = Modifier,
    backgroundGradient: Brush,
    onMealClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colorScheme.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(24.dp),
    ) {
        Canvas(
            modifier = modifier
                .align(Alignment.TopCenter)
                .size(300.dp)
                .blur(
                    radius = 120.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
        ) {
            drawCircle(brush = backgroundGradient)
        }
        Text(
            text = "급식",
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseSurface,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(230.dp),
                painter = painterResource(R.drawable.img_food),
                contentDescription = null,
            )
            DmsButton(
                text = "오늘의 급식 확인하기",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                onClick = onMealClick,
            )
        }
    }
}
