package team.aliens.dms.android.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.designsystem.annotation.DormDeprecated
import team.aliens.dms.android.designsystem.color.DormColor
import team.aliens.dms.android.designsystem.extension.Space
import team.aliens.dms.android.designsystem.modifier.dormClickable
import team.aliens.dms.android.designsystem.modifier.dormShadow
import team.aliens.dms.android.designsystem.typography.Body3
import team.aliens.dms.android.designsystem.typography.Body5

data class Survey(
    val title: String,
    val createAt: String,
    val state: SurveyState,
    val startAt: String,
    val finishAt: String,
)

enum class SurveyState(name: String) {
    ToDo("진행중"), InProgress("시작전"), Done("종료"),
}

@DormDeprecated
@Composable
fun SurveyList(
    modifier: Modifier = Modifier,
    surveys: List<Survey>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(items = surveys) { index, survey ->
            Survey(
                survey = survey,
                index = index,
                onClick = onClick,
            )

            if (index != surveys.size) {
                this@LazyColumn.Space(space = 12.dp)
            }
        }
    }
}

@Stable
private val SurveyHorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
private fun Survey(
    survey: Survey,
    index: Int,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = DormColor.Gray100,
                shape = RoundedCornerShape(6.dp),
            )
            .fillMaxWidth()
            .height(148.dp)
            .dormShadow(
                color = DormColor.Gray100,
                offsetX = 1.dp,
                offsetY = 1.dp,
            )
            .dormClickable(
                rippleEnabled = true,
            ) {
                onClick(index)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.padding(SurveyHorizontalPadding),
        ) {
            Body3(
                text = survey.title,
                color = DormColor.Gray800,
            )

            Space(space = 18.dp)

            Body5(
                text = survey.createAt,
                color = DormColor.Gray500,
            )

            Space(space = 11.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            color = DormColor.Darken200,
                            shape = CircleShape,
                        ),
                )

                Space(space = 10.dp)

                Body5(
                    text = survey.state.name,
                    color = DormColor.Gray700,
                )

                Space(space = 20.dp)

                Body3(
                    text = "${survey.startAt} ~ ${survey.finishAt}",
                    color = DormColor.Darken200,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSurvey() {
    val surveys = listOf(
        Survey(
            title = "이러이러한 상황",
            createAt = "2022/08/01",
            state = SurveyState.InProgress,
            startAt = "2022/08/02",
            finishAt = "2022/08/22",
        ),
        Survey(
            title = "이러이러한 상황",
            createAt = "2022/08/01",
            state = SurveyState.InProgress,
            startAt = "2022/08/02",
            finishAt = "2022/08/22",
        ),
    )

    SurveyList(
        surveys = surveys,
        onClick = {},
    )
}