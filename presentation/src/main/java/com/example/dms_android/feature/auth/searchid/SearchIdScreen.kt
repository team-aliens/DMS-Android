package com.example.dms_android.feature.auth.searchid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.Body4
import com.example.dms_android.R

@Preview
@Composable
fun SearchIdScreen() {
    var gradeValue by remember { mutableStateOf("") }
    var classRoomValue by remember { mutableStateOf("") }
    var numberValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray200)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "backButton",
            )
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = DormIcon.Applicate.drawableId),
                contentDescription = null,
                modifier = Modifier.size(49.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(7.dp)
            )
            Body4(
                text = stringResource(R.string.FindId),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(60.dp)
            )
            //TODO: Where is Dropdown?
            Spacer(modifier = Modifier.height(37.dp))
            DormTextField(
                value = nameValue,
                onValueChange = { nameValue = it },
                hint = stringResource(R.string.Name),
            )
            Spacer(
                modifier = Modifier
                    .height(37.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                //TODO: Toast완성되면 에러처리함
                DormTextField(
                    value = gradeValue,
                    onValueChange = { gradeValue = it },
                    hint = stringResource(R.string.grade),
                    modifier = Modifier.width(110.dp),
                )
                DormTextField(
                    value = classRoomValue,
                    onValueChange = { classRoomValue = it },
                    hint = stringResource(R.string.class_room),
                    modifier = Modifier.width(110.dp),
                )
                DormTextField(
                    value = numberValue,
                    onValueChange = { numberValue = it },
                    hint = stringResource(R.string.number),
                    modifier = Modifier.width(110.dp),
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.FindId),
                    color = DormButtonColor.Blue,
                    enabled = false,
                ) {
                }
            }
        }
    }
}