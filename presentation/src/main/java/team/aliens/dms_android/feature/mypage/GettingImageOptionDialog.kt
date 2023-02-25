package team.aliens.dms_android.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import team.aliens.design_system.dialog.DormBottomAlignedSingleButtonDialog
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.dms_android.component.GettingImageOptionItem
import team.aliens.dms_android.util.fetchImage
import team.aliens.presentation.R
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun GettingImageOptionDialog(
    onCancel: () -> Unit,
    onTakePhoto: (File) -> Unit,
    onSelectPhoto: (File) -> Unit,
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    DormCustomDialog(
        onDismissRequest = {
            /* explicit blank */
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        DormBottomAlignedSingleButtonDialog(
            btnText = stringResource(R.string.Cancel),
            onBtnClick = onCancel,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                // 사진 촬영
                GettingImageOptionItem(
                    icon = R.drawable.ic_camera,
                    text = stringResource(R.string.TakePhoto),
                ) {

                    //onTakePhoto() todo
                }

                // 사진 선택
                GettingImageOptionItem(
                    icon = R.drawable.ic_photo,
                    text = stringResource(R.string.SelectPhoto),
                ) {
                    scope.launch {

                        val file = fetchImage(
                            context = context,
                        )

                        if (file != null)
                            onSelectPhoto(file)
                    }
                }
            }
        }
    }
}