package team.aliens.dms.android.feature.findid.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField

@Composable
internal fun UserInfoInputContent(
    name: String,
    grade: String,
    classroom: String,
    number: String,
    onNameChange: (String) -> Unit,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        DmsTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "이름 입력",
            label = "이름",
            value = name,
            onValueChange = onNameChange,
        )
        StudentNumberInputs(
            grade = grade,
            classroom = classroom,
            number = number,
            onGradeChange = onGradeChange,
            onClassroomChange = onClassroomChange,
            onNumberChange = onNumberChange,
        )
    }
}

@Composable
private fun StudentNumberInputs(
    grade: String,
    classroom: String,
    number: String,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val classroomFocusRequest = remember { FocusRequester() }
    val numberFocusRequest = remember { FocusRequester() }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsTextField(
            modifier = Modifier.weight(1f),
            value = grade,
            onValueChange = { grade ->
                onGradeChange(grade)
                if (grade.isNotEmpty()) classroomFocusRequest.requestFocus()
            },
            label = "학년",
            hint = "학년 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(classroomFocusRequest),
            value = classroom,
            onValueChange = { classroom ->
                onClassroomChange(classroom)
                if (classroom.isNotEmpty()) numberFocusRequest.requestFocus()
            },
            label = "반",
            hint = "반 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(numberFocusRequest),
            value = number,
            onValueChange = onNumberChange,
            label = "번호",
            hint = "번호 입력",
            keyboardType = KeyboardType.Number,
        )
    }
}
