package team.aliens.dms.android.designsystem.component

/*

@Stable
val DefaultPhotoSize: Dp = 100.dp

@Stable
val PhotoShape: Shape = RoundedCornerShape(6.dp)

@DormDeprecated
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoList(
    modifier: Modifier = Modifier,
    photos: List<String>,
    onClickAdd: () -> Unit,
    onClickDelete: (Int) -> Unit,
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(DefaultPhotoSize),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            AddPhoto {
                onClickAdd()
            }
        }

        itemsIndexed(photos) { index, item ->
            HorizontalAnimationBox() {
                Photo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    photoUrl = item,
                    index = index,
                    onClick = onClickDelete,
                )
            }
        }
    }
}

@Composable
private fun Photo(
    modifier: Modifier = Modifier,
    photoUrl: String,
    index: Int,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(DefaultPhotoSize)
                .background(
                    color = Color.Transparent,
                )
                .dormClickable(
                    rippleEnabled = true,
                ) {
                    onClick(index)
                }
                .clip(
                    PhotoShape,
                ),
            model = photoUrl,
            contentDescription = null,
        )

        Image(
            modifier = Modifier.padding(
                top = 8.dp,
                end = 8.dp,
            ),
            painter = painterResource(
                id = R.drawable.ic_photo_close,
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun AddPhoto(
    onClickAddPhoto: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(DefaultPhotoSize)
            .background(
                color = DormColor.Gray300,
                shape = PhotoShape,
            )
            .dormClickable(
                rippleEnabled = true,
            ) {
                onClickAddPhoto()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = DormIcon.Plus.drawableId,
            ),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PreviewPhoto() {
    val fakeImage =
        "https://media.istockphoto.com/vectors/male-profile-flat-blue-simple-icon-with-long-shadow-vector-id522855255?k=20&m=522855255&s=612x612&w=0&h=fLLvwEbgOmSzk1_jQ0MgDATEVcVOh_kqEe0rqi7aM5A="

    val photos = remember { mutableStateListOf<String>() }

    PhotoList(
        photos = photos,
        onClickAdd = { photos.add(fakeImage) },
        onClickDelete = { photos.removeAt(it) },
    )
}*/
