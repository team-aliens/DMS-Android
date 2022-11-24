package com.example.dms_android.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R

@Composable
fun TopBar(
    title: String
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = DormColor.Gray100),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .width(25.dp)
        )
        Image(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(id = DormIcon.BackArrow.drawableId),
            contentDescription = stringResource(id = R.string.backButton),
        )
        Spacer(
            modifier = Modifier
                .width(45.dp),
        )
        SubTitle2(text = title)
    }
}
