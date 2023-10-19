package team.aliens.dms.android.designsystem.component

/*

data class Property(
    val propertyImage: String,
    val title: String,
    val userName: String,
    val profileImage: String,
    val createAt: String,
)

@DormDeprecated
@Composable
fun DormLostPropertyList(
    modifier: Modifier = Modifier,
    propertyList: List<Property>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(propertyList) { index, property ->
            LostProperty(
                property = property,
                index = index,
                onClick = onClick,
            )
        }
    }
}

@Stable
private val LostPropertyHeight: Dp = 136.dp

@Stable
private val LostPropertyShape: Shape = RoundedCornerShape(6.dp)

@Stable
private val LostPropertyImageSize: Dp = 100.dp

@Stable
private val LostPropertyProfileSize: Dp = 24.dp

@DormDeprecated
@Composable
private fun LostProperty(
    property: Property,
    index: Int,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LostPropertyHeight)
            .background(
                color = DormColor.Gray100,
                shape = LostPropertyShape,
            )
            .dormClickable(
                rippleEnabled = true,
            ) {
                onClick(index)
            }
            .dormShadow(
                color = DormColor.Gray400,
                offsetY = 1.dp,
                offsetX = 1.dp,
            ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(LostPropertyImageSize)
                    .background(
                        color = Color.Transparent,
                        shape = LostPropertyShape,
                    ),
                model = property.propertyImage,
                contentDescription = null,
            )

            Space(space = 16.dp)

            Column {
                Body5(
                    text = property.title,
                    color = DormColor.Gray700,
                )

                Space(space = 23.dp)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(LostPropertyProfileSize)
                            .background(color = Color.Transparent)
                            .clip(CircleShape),
                        model = property.profileImage,
                        contentDescription = null,
                    )

                    Space(space = 10.dp)

                    Caption(
                        text = property.userName,
                        color = DormColor.Gray600,
                    )
                }

                Space(space = 7.dp)

                Caption(
                    text = property.createAt,
                    color = DormColor.Gray600,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLostProperty() {
    val fakeImage =
        "https://media.istockphoto.com/vectors/male-profile-flat-blue-simple-icon-with-long-shadow-vector-id522855255?k=20&m=522855255&s=612x612&w=0&h=fLLvwEbgOmSzk1_jQ0MgDATEVcVOh_kqEe0rqi7aM5A="

    val properties = listOf(
        Property(
            profileImage = fakeImage,
            title = "파란색 에어팟을 봤습니다.",
            propertyImage = fakeImage,
            userName = "2118 임세현",
            createAt = "22/10/07",
        ),
        Property(
            profileImage = fakeImage,
            title = "파란색 에어팟을 봤습니다.",
            propertyImage = fakeImage,
            userName = "2118 임세현",
            createAt = "22/10/07",
        ),
    )

    DormLostPropertyList(
        propertyList = properties,
        onClick = {},
    )
}*/
