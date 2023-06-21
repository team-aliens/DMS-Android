package team.aliens.dms_android.feature.main.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import team.aliens.design_system.dialog.DormBottomAlignedSingleButtonDialog
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.toast.rememberToast
import team.aliens.dms_android.component.GettingImageOptionItem
import team.aliens.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun GettingImageOptionDialog(
    onCancel: () -> Unit,
    onTakePhoto: () -> Unit,
    onSelectPhoto: () -> Unit,
    onDialogDismiss: () -> Unit,
) {

    val toast = rememberToast()

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
            onBackgroundPress = onDialogDismiss,
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

                    toast("죄송합니다, 현재 기능을 사용할 수 없습니다.") // todo replace with taking photo with camera

                    //onTakePhoto()
                }

                // 사진 선택
                GettingImageOptionItem(
                    icon = R.drawable.ic_photo,
                    text = stringResource(R.string.SelectPhoto),
                ) {
                    onSelectPhoto()
                }
            }
        }
    }
}