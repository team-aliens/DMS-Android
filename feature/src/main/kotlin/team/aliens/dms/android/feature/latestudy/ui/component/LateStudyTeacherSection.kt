package team.aliens.dms.android.feature.latestudy.ui.component

import TeacherResponse
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM

@Composable
fun LateStudyTeacherSection(
    value: String,
    onValueChange: (String) -> Unit,
    teachers: List<TeacherResponse>,
    onTeacherClick: (TeacherResponse) -> Unit,
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
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = DmsTheme.typography.bodyM.copy(
                    color = DmsTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "홍길동",
                            color = DmsTheme.colorScheme.inverseSurface,
                            style = DmsTheme.typography.bodyM,
                        )
                    }
                    innerTextField()
                },
            )
        }
    }
}
