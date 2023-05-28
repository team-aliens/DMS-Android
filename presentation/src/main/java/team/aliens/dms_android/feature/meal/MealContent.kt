/*
package team.aliens.dms_android.feature.meal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.dms_android.widget.meal.MealType
import java.time.LocalDateTime
import kotlin.math.absoluteValue

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScrollEffectPager(
    mealViewModel: MealViewModel,
) {

    var currentPageIndex by remember { mutableStateOf(0) }

    val currentMealType = MealType.getCurrentMealType(LocalDateTime.now())
    currentPageIndex = when (currentMealType) {
        MealType.Breakfast -> 0
        MealType.Lunch -> 1
        else -> 2
    }

    val pagerState = rememberPagerState(currentPageIndex)

    val state = mealViewModel.state.collectAsState().value

    var mealState by remember {
        mutableStateOf(state.meals.value)
    }

    LaunchedEffect(state) {
        state.meals.collect {
            mealState = it
        }
    }

    val vib = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    var firstEnter by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(pagerState.currentPage) {
        if (firstEnter) {
            firstEnter = false
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vib.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                )
            }
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentPadding = PaddingValues(
            horizontal = 64.dp,
        ),
        count = 3,
    ) { pageIndex ->

        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    )
                }
                .background(
                    color = DormTheme.colors.background,
                    shape = RoundedCornerShape(20.dp),
                )
                .clip(
                    RoundedCornerShape(20.dp),
                )
                .border(
                    width = 1.dp,
                    color = DormTheme.colors.primary,
                    shape = RoundedCornerShape(20.dp),
                ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                when (pageIndex) {
                    0 -> {
                        MenuListLayout(
                            icon = DormIcon.Breakfast,
                            menus = mealState.breakfast,
                        )
                    }
                    1 -> {
                        MenuListLayout(
                            icon = DormIcon.Lunch,
                            menus = mealState.lunch,
                        )
                    }
                    2 -> {
                        MenuListLayout(
                            icon = DormIcon.Dinner,
                            menus = mealState.dinner,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuListLayout(
    icon: DormIcon,
    menus: Pair<List<String>, String>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {
            Icon(
                painter = painterResource(
                    id = icon.drawableId,
                ),
                contentDescription = null,
                tint = DormTheme.colors.primaryVariant,
            )
        }

        items(
            menus.first,
        ) { menu ->
            Body4(
                text = menu,
            )
        }

        item {
            Body4(
                text = menus.second,
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}*/
