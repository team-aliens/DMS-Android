package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM

private const val REASON_MAX_LENGTH = 200

@Composable
fun LateStudyReasonSection(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Text(
            text = "사유",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyB,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .background(
                    color = DmsTheme.colorScheme.background,
                    shape = RoundedCornerShape(20.dp),
                )
                .height(180.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (value.isNotEmpty()) {
                Text(
                    text = "${value.length}/$REASON_MAX_LENGTH",
                    color = DmsTheme.colorScheme.inverseSurface,
                    style = DmsTheme.typography.bodyM,
                )
            }

            BasicTextField(
                value = value,
                onValueChange = { changedValue ->
                    if (changedValue.length <= REASON_MAX_LENGTH) {
                        onValueChange(changedValue)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = DmsTheme.typography.bodyM.copy(
                    color = DmsTheme.colorScheme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "새벽 자습을 신청한 이유를 작성해주세요",
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
