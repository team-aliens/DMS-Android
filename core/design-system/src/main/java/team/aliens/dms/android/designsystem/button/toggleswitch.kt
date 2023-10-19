package team.aliens.dms.android.designsystem.button

/*

@Stable
private val ToggleSwitchWidth = 38.dp

@Stable
private val ToggleSwitchHeight = 20.dp

@DormDeprecated
@Composable
fun ToggleSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {

    val yOffset: Dp by animateDpAsState(targetValue = if (checked) 17.dp else 0.dp)

    val circleBackgroundColor: Color by animateColorAsState(targetValue = if (checked) DormColor.DormPrimary else DormColor.Gray100)

    val backgroundColor: Color by animateColorAsState(targetValue = if (checked) DormColor.Lighten100 else DormColor.Gray500)

    Box(
        modifier = modifier.size(
            width = ToggleSwitchWidth,
            height = ToggleSwitchHeight,
        ).dormClickable(
            rippleEnabled = false,
        ) {
            onToggle(!checked)
        },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp),
            ))

        Box(modifier = Modifier
            .padding(start = yOffset)
            .size(20.dp)
            .background(
                color = circleBackgroundColor,
                shape = CircleShape,
            )
            .dormShadow(
                color = DormColor.Gray400,
                offsetY = 1.dp,
            ))
    }
}

@Preview
@Composable
fun PreviewToggleSwitch() {
    var checked by remember { mutableStateOf(false) }

    ToggleSwitch(
        checked = checked,
        onToggle = { checked = !checked },
    )
}*/
