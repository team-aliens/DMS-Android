package team.aliens.dms.android.feature.chatbot.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
internal data class ChatBotState(
    val input: String = "",
    val suggestions: List<String> = listOf(
        "외출 신청은 언제까지 해야 해?",
        "점호 시간 알려줘",
        "세탁실 이용 시간이 궁금해",
        "벌점 기준 알려줘",
    ),
)
