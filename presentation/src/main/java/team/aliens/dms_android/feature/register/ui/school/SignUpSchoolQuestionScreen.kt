package team.aliens.dms_android.feature.register.ui.school

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.school.CompareSchoolAnswerSuccess
import team.aliens.dms_android.feature.register.event.school.FetchSchoolQuestion
import team.aliens.dms_android.feature.register.event.school.MissMatchCompareSchool
import team.aliens.dms_android.feature.register.event.school.NotFoundCompareSchool
import team.aliens.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel
import team.aliens.presentation.R
import java.util.*

@Composable
fun SignUpSchoolQuestionScreen(
    navController: NavController,
    confirmSchoolViewModel: ConfirmSchoolViewModel = hiltViewModel(),
) {

    var schoolQuestion by remember { mutableStateOf("") }
    var schoolAnswer by remember { mutableStateOf("") }

    val toast = rememberToast()

    val context = LocalContext.current

    var isError by remember { mutableStateOf(false) }

    var schoolId by remember { mutableStateOf(UUID.randomUUID()) }

    val onAnswerChange = { value: String ->
        if(isError && value.length != schoolAnswer.length) isError = false
        schoolAnswer = value
    }

    LaunchedEffect(Unit) {
        schoolId = UUID.fromString(
            navController.previousBackStackEntry?.arguments?.getString("schoolId"),
        )
        confirmSchoolViewModel.schoolQuestion(schoolId)
        confirmSchoolViewModel.confirmSchoolEvent.collect {
            when (it) {
                is FetchSchoolQuestion -> {
                    schoolQuestion = it.schoolConfirmQuestionEntity.question
                }
                is NotFoundCompareSchool -> {
                    isError = true
                    toast(context.getString(R.string.CheckSchoolCode))
                }
                is CompareSchoolAnswerSuccess -> {
                    isError = false
                    navController.currentBackStackEntry?.arguments?.run {
                        putString("schoolCode", navController.previousBackStackEntry?.arguments?.getString("schoolCode"))
                        putString("schoolAnswer", schoolAnswer)
                        putString("schoolId", schoolId.toString())
                    }
                    navController.navigate(NavigationRoute.SignUpEmail)
                }
                is MissMatchCompareSchool -> {
                    isError = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.843f)
        ) {
            AppLogo()
            Spacer(modifier = Modifier.height(8.dp))
            Body2(
                text = stringResource(id = R.string.QuestionConfirmSchool)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Body2(text = schoolQuestion)
            Spacer(modifier = Modifier.height(10.dp))
            DormTextField(
                value = schoolAnswer,
                onValueChange = onAnswerChange,
                hint = stringResource(id = R.string.Reply),
                error = isError,
                errorDescription = stringResource(id = R.string.InconsistentSchoolReply),
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Check),
            color = DormButtonColor.Blue,
            enabled = (schoolAnswer.isNotEmpty() && !isError),
        ) {
            confirmSchoolViewModel.compareSchoolAnswer(
                schoolId = schoolId,
                answer = schoolAnswer,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Caption(
                text = stringResource(id = R.string.AlreadyAccount),
                color = DormColor.Gray500,
            )
            Spacer(modifier = Modifier.width(8.dp))
            ButtonText(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .dormClickable(
                        rippleEnabled = false,
                    ) {
                        navController.navigate(NavigationRoute.Login){
                            popUpTo(NavigationRoute.Login){
                                inclusive = true
                            }
                        }
                    },
                text = stringResource(id = R.string.Login),
                color = DormColor.Gray900,
            )
        }
    }
}