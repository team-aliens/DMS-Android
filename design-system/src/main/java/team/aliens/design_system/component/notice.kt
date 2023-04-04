package team.aliens.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.OverLine
import team.aliens.design_system.extension.Space

data class Notice(
    var noticeId: String,
    var title: String,
    var createAt: String,
)

@DormDeprecated
@Composable
fun NoticeList(
    modifier: Modifier = Modifier,
    notices: List<Notice>,
    errorMessage: String = "",
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(items = notices) { index, notice ->
            Notice(
                notice = notice,
                onClick = onClick,
            )

            if (index != notices.size) {
                this@LazyColumn.Space(space = 12.dp)
            }
        }
        if (notices.isEmpty()) {
            item {
                Body3(
                    text = errorMessage,
                )
            }
        }
    }
}

@Composable
private fun Notice(
    notice: Notice,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .dormShadow(
                color = DormTheme.colors.secondaryVariant,
                offsetX = 1.dp,
                offsetY = 1.dp,
            )
            .background(
                color = DormTheme.colors.surface,
            )
            .dormClickable {
                onClick(notice.noticeId)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp),
        ) {
            Body4(
                modifier = Modifier.padding(top = 12.dp),
                text = notice.title,
            )

            Space(space = 4.dp)

            OverLine(
                modifier = Modifier.padding(bottom = 12.dp),
                text = notice.createAt,
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}

@Preview
@Composable
fun PreviewNotice() {
    val notices = listOf(
        Notice(
            noticeId = "asd",
            title = "방 좀 치우고 살아주세요 ㅎㅎ",
            createAt = "2022.10.14 PM 08:33",
        ),
        Notice(
            noticeId = "asd",
            title = "방에서도 마스크를 착용해주세요",
            createAt = "2022.10.15 PM 08:33",
        ),
    )

    NoticeList(
        notices = notices,
        onClick = { noticeId ->
            print(noticeId)
        },
    )
}