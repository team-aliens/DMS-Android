package team.aliens.dms.android.designsystem.button

/*

@DormDeprecated
@Composable
fun BasicRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    selectedColor: Color,
    unSelectedColor: Color,
    disabledSelectedColor: Color,
    disabledUnSelectedColor: Color,
    enabled: Boolean = true,
) {
    val enabledBackgroundColor: Color by animateColorAsState(if (checked) selectedColor else unSelectedColor)

    val disabledBackgroundColor: Color by animateColorAsState(if (checked) disabledSelectedColor else disabledUnSelectedColor)

    val backgroundColor = if (enabled) enabledBackgroundColor else disabledBackgroundColor

    val inCircleSize: Dp by animateDpAsState(if (checked) 10.dp else 0.dp)

    Box(
        modifier = modifier.border(width = 2.dp, shape = CircleShape, color = backgroundColor)
            .runIf(enabled) {
                composed {
                    dormClickable(
                        rippleEnabled = false,
                        rippleColor = null,
                    ) {
                        onCheckedChange(!checked)
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier
            .size(inCircleSize)
            .background(
                color = backgroundColor,
                shape = CircleShape,
            ))
    }
}

@Stable
private val DefaultRadioButtonSize: Dp = 20.dp

@Composable
fun DormBasicRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    BasicRadioButton(
        modifier = modifier.size(DefaultRadioButtonSize),
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        selectedColor = DormColor.DormPrimary,
        unSelectedColor = DormColor.Gray500,
        disabledSelectedColor = DormColor.Lighten100,
        disabledUnSelectedColor = DormColor.Gray300,
    )
}

@Composable
fun DormTextRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    Row(
        modifier = modifier.runIf(enabled) {
            composed {
                dormClickable(
                    rippleEnabled = false,
                    rippleColor = null,
                ) {
                    onCheckedChange(!checked)
                }
            }
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DormBasicRadioButton(
            modifier = modifier,
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
        )

        Space(space = 15.dp)

        Box(
            modifier = Modifier.offset(
                y = (-1.2).dp,
            ),
        ) {
            Body5(
                text = text,
                color = DormColor.Gray700,
            )
        }
    }
}


@Preview
@Composable
fun PreviewRadioButton() {
    var checked by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    DormBasicRadioButton(
        checked = checked,
        onCheckedChange = { checked = !checked },
    )

    DormTextRadioButton(
        checked = checked2,
        onCheckedChange = { checked2 = !checked2 },
        text = "항목 1",
    )
}*/
