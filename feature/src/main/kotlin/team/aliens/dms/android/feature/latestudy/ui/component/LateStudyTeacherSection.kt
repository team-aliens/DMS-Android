package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM

@Composable
fun LateStudyTeacherSection(
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Text(
            text = "담당 선생님",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyB,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                )
                .background(
                    color = DmsTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 18.dp,
                ),
        ) {
            Text(
                text = "홍길동",
                color = DmsTheme.colorScheme.inverseSurface,
                style = DmsTheme.typography.bodyM,
            )
        }
    }
}
