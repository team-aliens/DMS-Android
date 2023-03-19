package team.aliens.design_system.extension

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.Space(
    space: Dp = 0.dp,
    ratio: Float? = null,
) {
    if(ratio != null){
        Spacer(modifier = Modifier.weight(ratio))
    }else {
        Spacer(modifier = Modifier.height(space))
    }
}

@Composable
fun RowScope.Space(
    space: Dp = 0.dp,
    ratio: Float? = null,
) {
    if(ratio != null){
        Spacer(modifier = Modifier.weight(ratio))
    }else {
        Spacer(modifier = Modifier.width(space))
    }
}

@Composable
fun LazyListScope.Space(
    space: Dp = 0.dp,
) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun RatioSpace(
    width: Float? = null,
    height: Float? = null,
){
    if(width != null){
        Spacer(modifier = Modifier.fillMaxWidth(width))
    }else{
        Spacer(modifier = Modifier.fillMaxHeight(height!!))
    }
}