package team.aliens.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.OverLine

data class Notice(
    var noticeId: String,
    var title: String,
    var createdAt: String,
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
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 64.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = notices.size,
        ) { index ->
            Notice(
                notice = notices[index],
                onClick = onClick,
            )
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
            .shadow(
                shape = RoundedCornerShape(
                    size = 6.dp,
                ),
                elevation = 6.dp,
            )
            .clip(shape = RoundedCornerShape(6.dp))
            .background(
                color = DormTheme.colors.surface,
            )
            .dormClickable(
                rippleEnabled = true,
            ) {
                onClick(notice.noticeId)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Body4(
                modifier = Modifier.padding(top = 12.dp),
                text = notice.title,
            )

            Space(space = 4.dp)

            OverLine(
                modifier = Modifier.padding(bottom = 12.dp),
                text = notice.createdAt.toNoticeDate(),
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}

fun String.toNoticeDate() = StringBuilder().apply {
    append(this@toNoticeDate.split('T')[0])
    append(" ")
    append(this@toNoticeDate.split('T')[1].split(':')[0])
    append(":")
    append(this@toNoticeDate.split('T')[1].split(':')[1])
}.toString()