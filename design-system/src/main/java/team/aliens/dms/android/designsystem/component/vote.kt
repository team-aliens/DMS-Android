package team.aliens.dms.android.designsystem.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.designsystem.annotation.DormDeprecated
import team.aliens.dms.android.designsystem.color.DormColor
import team.aliens.dms.android.designsystem.extension.Space
import team.aliens.dms.android.designsystem.toast.rememberToast
import team.aliens.dms.android.designsystem.typography.Body3
import team.aliens.dms.android.designsystem.typography.Caption
import team.aliens.dms.android.designsystem.button.DormButtonColor
import team.aliens.dms.android.designsystem.button.DormContainedLargeButton
import team.aliens.dms.android.designsystem.button.DormOutlineLargeButton
import team.aliens.dms.android.designsystem.button.DormTextCheckBox
import team.aliens.dms.android.designsystem.button.DormTextRadioButton

data class VoteItem(
    val name: String,
    val number: Int? = null,
)

@Stable
private val DefaultVoteSelected = -1

@DormDeprecated
@Composable
fun Vote(
    modifier: Modifier = Modifier,
    title: String,
    votes: List<VoteItem>,
    onFinished: (Int) -> Unit,
    isFinished: Boolean = false,
    onResubmit: () -> Unit,
) {
    var selected by remember { mutableStateOf(DefaultVoteSelected) }
    val onChangeState: (Int) -> Unit = { selected = it }
    val isSelectedItem: (Int) -> Boolean = { selected == it }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(6.dp),
                color = DormColor.Gray300,
            )
            .background(
                shape = RoundedCornerShape(6.dp),
                color = DormColor.Gray100,
            )
    ) {
        Column(
            modifier = Modifier.padding(all = 20.dp)
        ) {
            Body3(
                text = title,
                color = DormColor.Gray700,
            )

            Space(space = 36.dp)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                itemsIndexed(votes) { index, vote ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        DormTextRadioButton(
                            text = vote.name,
                            checked = isSelectedItem(index),
                            onCheckedChange = { onChangeState(index) },
                            enabled = !isFinished,
                        )

                        if (vote.number != null) {
                            this@Row.Space(ratio = 1f)

                            Caption(
                                text = "${vote.number}명",
                                color = DormColor.Gray500,
                            )
                        }
                    }
                }
            }

            Space(space = 62.dp)

            if (!isFinished) {
                DormContainedLargeButton(
                    text = "제출하기",
                    color = DormButtonColor.Blue,
                    enabled = selected != DefaultVoteSelected,
                ) {
                    onFinished(selected)
                }
            } else {
                DormOutlineLargeButton(
                    text = "다시 제출하기",
                    color = DormButtonColor.Blue,
                    enabled = selected != DefaultVoteSelected,
                ) {
                    onResubmit()
                }
            }
        }
    }
}


data class CheckBoxState(
    val index: Int,
    val selected: Boolean = false,
)

@DormDeprecated
@SuppressLint("MutableCollectionMutableState")
@Composable
fun OverlapVote(
    modifier: Modifier = Modifier,
    title: String,
    votes: List<VoteItem>,
    onFinished: (List<Int>) -> Unit,
    isFinished: Boolean = false,
    onResubmit: () -> Unit,
) {

    val selected = remember { mutableStateListOf<Int>() }

    val checked: (Int) -> Boolean = { check ->
        selected.any { it == check }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(6.dp),
                color = DormColor.Gray300,
            )
            .background(
                shape = RoundedCornerShape(6.dp),
                color = DormColor.Gray100,
            )
    ) {
        Column(
            modifier = Modifier.padding(all = 20.dp)
        ) {
            Body3(
                text = title,
                color = DormColor.Gray700,
            )

            Space(space = 36.dp)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                itemsIndexed(votes) { index, vote ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DormTextCheckBox(
                            text = vote.name,
                            checked = checked(index),
                            onCheckedChange = {
                                if (checked(index)) {
                                    selected.remove(index)
                                } else {
                                    selected.add(index)
                                }
                            },
                            enabled = !isFinished,
                        )

                        if (vote.number != null) {
                            Space(ratio = 1f)

                            Caption(
                                text = "${vote.number}명",
                                color = DormColor.Gray500,
                            )
                        }
                    }
                }
            }

            Space(space = 62.dp)

            if (!isFinished) {
                DormContainedLargeButton(
                    text = "제출하기",
                    color = DormButtonColor.Blue,
                    enabled = selected.isNotEmpty(),
                ) {
                    onFinished(selected)
                }
            } else {
                DormOutlineLargeButton(
                    text = "다시 제출하기",
                    color = DormButtonColor.Blue,
                    enabled = selected.isNotEmpty(),
                ) {
                    onResubmit()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewVote() {
    var finished by remember { mutableStateOf(false) }

    val votes = listOf(
        VoteItem("아침", 1),
        VoteItem("점심", 2),
        VoteItem("저녁", 3),
    )

    val toast = rememberToast()

    OverlapVote(
        modifier = Modifier.padding(16.dp),
        title = "어떤 급식이 맛있나요?",
        votes = votes,
        onFinished = {
            toast(
                message = it.toString()
            )

            finished = true
        },
        isFinished = finished,
        onResubmit = {
            finished = false
        }
    )
}