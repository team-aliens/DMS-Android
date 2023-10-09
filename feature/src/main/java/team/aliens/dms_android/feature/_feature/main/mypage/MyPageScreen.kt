package team.aliens.dms_android.feature._feature.main.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.glide.GlideImage
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.DefaultAppliedTagSize
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Headline3
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.feature._feature.image.SelectImageTypeDialog
import team.aliens.dms_android.feature.R
import team.aliens.domain.model._common.Sex

@Destination
@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPasswordNav: () -> Unit,
    onNavigateToAuthNav: () -> Unit,
    pointServiceEnabled: Boolean,
) {
    val uiState by myPageViewModel.stateFlow.collectAsStateWithLifecycle()
    val myPageInformation = uiState.myPage
    var signOutDialogState by remember { mutableStateOf(false) }
    var withdrawDialogState by remember { mutableStateOf(false) }
    var profileDialogState by remember { mutableStateOf(false) }

    if (signOutDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                /* explicit blank */
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_sign_out),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.SignOut)
                    onNavigateToAuthNav()
                    signOutDialogState = false
                },
                onSubBtnClick = {
                    signOutDialogState = false
                },
            )
        }
    }
    if (withdrawDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                /* explicit blank */
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_withdraw),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.Withdraw)
                    onNavigateToAuthNav()
                    withdrawDialogState = false
                },
                onSubBtnClick = {
                    withdrawDialogState = false
                },
            )
        }
    }
    if (profileDialogState) {
        SelectImageTypeDialog(
            onCancel = {
                profileDialogState = false
            },
            onTakePhoto = onNavigateToUploadProfileImageWithTakingPhoto,
            onSelectPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
            onDialogDismiss = {
                profileDialogState = false
            },
        )
    }
    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        myPageInformation?.run {
            Spacer(Modifier.height(24.dp))
            UserInformation(
                modifier = Modifier.fillMaxWidth(),
                gradeClassNumber = gradeClassNumber,
                name = name,
                sex = sex,
                school = schoolName,
                profileImageUrl = profileImageUrl,
                onChangeProfileImage = { profileDialogState = !profileDialogState },
            )
            if (pointServiceEnabled) PointsInformation(
                modifier = Modifier.fillMaxWidth(),
                phrase = phrase,
                bonusPoint = bonusPoint,
                minusPoint = minusPoint,
            )
        }
        Options(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onPointHistoryClicked = if (pointServiceEnabled) onNavigateToPointHistory else null,
            onEditPasswordClicked = onNavigateToEditPasswordNav,
            onSignOutClicked = { signOutDialogState = !signOutDialogState },
            onWithdrawClicked = { withdrawDialogState = !withdrawDialogState },
        )
    }
}

@Composable
private fun UserInformation(
    modifier: Modifier = Modifier,
    gradeClassNumber: String,
    name: String,
    sex: Sex,
    school: String,
    profileImageUrl: String,
    onChangeProfileImage: () -> Unit,
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(0.8f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Title1(
                    text = "$gradeClassNumber $name",
                )
                Spacer(Modifier.width(20.dp))
                LastAppliedItem(
                    modifier = DefaultAppliedTagSize,
                    text = when (sex) {
                        Sex.MALE -> stringResource(R.string.male)
                        Sex.FEMALE -> stringResource(R.string.female)
                        else -> throw IllegalStateException()
                    },
                    backgroundColor = when (sex) {
                        Sex.MALE -> DormColor.Lighten200
                        Sex.FEMALE -> DormColor.LightenError
                        else -> throw IllegalStateException()
                    },
                    textColor = when (sex) {
                        Sex.MALE -> DormColor.DormPrimary
                        Sex.FEMALE -> DormColor.Error
                        else -> throw IllegalStateException()
                    },
                )
            }
            Spacer(Modifier.height(10.dp))
            Body5(
                text = school,
            )
        }
        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.BottomEnd,
        ) {
            GlideImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .dormClickable { onChangeProfileImage() },
                imageModel = { profileImageUrl },
            )
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_mypage_edit),
                contentDescription = null,
            )
        }
    }
}

private enum class PointCardType {
    BONUS, MINUS,
    ;
}

@Composable
private fun PointsInformation(
    modifier: Modifier = Modifier,
    phrase: String,
    bonusPoint: Int,
    minusPoint: Int,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PhraseCard(
            modifier = Modifier.fillMaxWidth(),
            phrase = phrase,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PointCard(
                modifier = Modifier.weight(1f),
                type = PointCardType.BONUS,
                point = bonusPoint,
            )
            PointCard(
                modifier = Modifier.weight(1f),
                type = PointCardType.MINUS,
                point = minusPoint,
            )
        }
    }
}

@Composable
private fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(
                DormTheme.colors.secondary,
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Caption(
            text = phrase,
            color = DormColor.Gray1000,
        )
    }
}

@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    type: PointCardType,
    point: Int,
) {
    val textColor = when (type) {
        PointCardType.BONUS -> DormTheme.colors.primary
        PointCardType.MINUS -> DormTheme.colors.error
    }
    Column(
        modifier = modifier
            .dormShadow(DormTheme.colors.primaryVariant)
            .clip(RoundedCornerShape(10.dp))
            .border(
                color = when (type) {
                    PointCardType.BONUS -> DormTheme.colors.primary
                    PointCardType.MINUS -> DormTheme.colors.error
                },
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DormTheme.colors.surface, // todo
            )
            .padding(
                vertical = 14.dp,
                horizontal = 20.dp,
            ),
    ) {
        Caption(
            text = stringResource(
                when (type) {
                    PointCardType.BONUS -> R.string.bonus_point
                    PointCardType.MINUS -> R.string.minus_point
                }
            ),
            color = textColor,
        )
        Spacer(Modifier.height(24.dp))
        Headline3(
            modifier = Modifier.fillMaxWidth(),
            text = point.toString(),
            textAlign = TextAlign.End,
            color = textColor,
        )
    }
}

// TODO 리스트 디자인시스템으로 커버 필요
@Composable
private fun Options(
    modifier: Modifier = Modifier,
    onPointHistoryClicked: (() -> Unit)?,
    onEditPasswordClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onWithdrawClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (onPointHistoryClicked != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormShadow(DormTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DormTheme.colors.surface),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onPointHistoryClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(R.string.my_page_check_point_history),
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = DormTheme.colors.secondaryVariant,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onEditPasswordClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(R.string.change_password),
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormShadow(DormTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DormTheme.colors.surface),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onEditPasswordClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                ) {
                    Body5(
                        text = stringResource(R.string.change_password),
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(DormTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DormTheme.colors.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormClickable { onSignOutClicked() }
                    .padding(
                        vertical = 14.dp,
                        horizontal = 16.dp,
                    ),
            ) {
                Body5(
                    text = stringResource(R.string.my_page_sign_out),
                    color = DormTheme.colors.error,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(DormTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DormTheme.colors.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormClickable { onWithdrawClicked() }
                    .padding(
                        vertical = 14.dp,
                        horizontal = 16.dp,
                    ),
            ) {
                Body5(
                    text = stringResource(R.string.my_page_withdrawal),
                    color = DormTheme.colors.error,
                )
            }
        }
    }
}
