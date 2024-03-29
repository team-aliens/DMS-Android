package team.aliens.dms.android.feature.editprofile.dialog
/*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import team.aliens.dms.android.designsystem.dialog.DormBottomAlignedSingleButtonDialog
import team.aliens.dms.android.designsystem.dialog.DormCustomDialog
import team.aliens.dms.android.designsystem.icon.DormIcon
import team.aliens.dms.android.designsystem.toast.rememberToast
import team.aliens.dms.android.feature._legacy.GettingImageOptionItem
import team.aliens.dms.android.feature.R

@Composable
internal fun SelectImageTypeDialog(
    onCancel: () -> Unit,
    onTakePhoto: () -> Unit,
    onSelectPhoto: () -> Unit,
    onDialogDismiss: () -> Unit,
) {

    val toast = rememberToast()

    DormCustomDialog(
        onDismissRequest = {
            */
/* explicit blank *//*

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
                GettingImageOptionItem(
                    icon = DormIcon.Camera.drawableId,
                    text = stringResource(R.string.TakePhoto),
                ) {
                    toast("죄송합니다, 현재 기능을 사용할 수 없습니다.") // todo replace with taking photo with camera
                    //onTakePhoto()
                }

                // 사진 선택
                GettingImageOptionItem(
                    icon = DormIcon.Photo.drawableId,
                    text = stringResource(R.string.SelectPhoto),
                ) {
                    onDialogDismiss()
                    onSelectPhoto()
                }
            }
        }
    }
}*/
