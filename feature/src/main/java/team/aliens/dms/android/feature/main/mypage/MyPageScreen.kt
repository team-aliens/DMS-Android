package team.aliens.dms.android.feature.main.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.designsystem.modifier.dormClickable
import team.aliens.dms.android.designsystem.modifier.dormShadow
import team.aliens.dms.android.designsystem.DmsTheme
import team.aliens.dms.android.designsystem.typography.Body5
import team.aliens.dms.android.designsystem.typography.Caption
import team.aliens.dms.android.designsystem.typography.Headline3
import team.aliens.dms.android.designsystem.typography.Title1
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.main.navigation.MainNavigator
import team.aliens.dms.android.shared.model.Sex

@Destination
@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    // myPageViewModel: MyPageViewModel = hiltViewModel(),
    navigator: MainNavigator,
    // onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    // onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    // pointServiceEnabled: Boolean,
) {/*
    val uiState by myPageViewModel.stateFlow.collectAsStateWithLifecycle()
    val myPageInformation = uiState.myPage
    var signOutDialogState by remember { mutableStateOf(false) }
    var withdrawDialogState by remember { mutableStateOf(false) }
    var profileDialogState by remember { mutableStateOf(false) }

    if (signOutDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                *//* explicit blank *//*
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_sign_out),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.SignOut)
                    navigator.openUnauthorizedNav()
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
                *//* explicit blank *//*
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_withdraw),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.Withdraw)
                    navigator.openUnauthorizedNav()
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
            onTakePhoto = { },
            onSelectPhoto = { },
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
            // TODO
            if (true) PointsInformation(
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
            // TODO
            onPointHistoryClicked = if (true) navigator::openPointHistory else null,
            onEditPasswordClicked = navigator::openEditPasswordNav,
            onSignOutClicked = { signOutDialogState = !signOutDialogState },
            onWithdrawClicked = { withdrawDialogState = !withdrawDialogState },
        )
    }*/
}
/*
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
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .dormClickable { onChangeProfileImage() },
                model = profileImageUrl,
                contentDescription = null,
            )
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_mypage_edit),
                contentDescription = null,
            )
        }
    }
}*//*

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
}*/
/*
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
                DmsTheme.colors.secondary,
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
}*/
/*
@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    type: PointCardType,
    point: Int,
) {
    val textColor = when (type) {
        PointCardType.BONUS -> DmsTheme.colors.primary
        PointCardType.MINUS -> DmsTheme.colors.error
    }
    Column(
        modifier = modifier
            .dormShadow(DmsTheme.colors.primaryVariant)
            .clip(RoundedCornerShape(10.dp))
            .border(
                color = when (type) {
                    PointCardType.BONUS -> DmsTheme.colors.primary
                    PointCardType.MINUS -> DmsTheme.colors.error
                },
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DmsTheme.colors.surface, // todo
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
}*/
/*

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
                    .dormShadow(DmsTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DmsTheme.colors.surface),
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
                    color = DmsTheme.colors.secondaryVariant,
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
                    .dormShadow(DmsTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DmsTheme.colors.surface),
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
                .dormShadow(DmsTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DmsTheme.colors.surface),
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
                    color = DmsTheme.colors.error,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(DmsTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DmsTheme.colors.surface),
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
                    color = DmsTheme.colors.error,
                )
            }
        }
    }
}
*/
