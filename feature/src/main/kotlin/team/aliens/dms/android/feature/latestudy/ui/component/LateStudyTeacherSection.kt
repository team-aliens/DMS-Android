package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.network.latestudy.model.TeacherResponse

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = DmsTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(16.dp),
                                )
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "홍길동",
                                    color = DmsTheme.colorScheme.inverseSurface,
                                    style = DmsTheme.typography.bodyM,
                                )
                            }
                            innerTextField()
                        }
                    },
                )
            }

            if (value.isNotBlank() && teachers.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 84.dp)
                        .zIndex(1f)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false,
                        )
                        .background(
                            color = DmsTheme.colorScheme.background,
                            shape = RoundedCornerShape(24.dp),
                        )
                        .heightIn(max = 220.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 12.dp),
                ) {
                    teachers.forEach { teacher ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTeacherClick(teacher) }
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "검색",
                                tint = DmsTheme.colorScheme.inverseSurface,
                                modifier = Modifier.size(20.dp),
                            )

                            Text(
                                text = teacher.name,
                                color = DmsTheme.colorScheme.onBackground,
                                style = DmsTheme.typography.bodyM,
                            )
                        }
                    }
                }
            }
        }
    }
}
