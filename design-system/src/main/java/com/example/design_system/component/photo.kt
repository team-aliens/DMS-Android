package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormClickable
import com.skydoves.landscapist.glide.GlideImage

@Stable
val DefaultPhotoSize: Dp = 100.dp

@Stable
val PhotoShape: Shape = RoundedCornerShape(6.dp)

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
            Photo(
                photoUrl = item,
                index = index,
                onClick = onClickDelete,
            )
        }
    }
}

@Composable
private fun Photo(
    photoUrl: String,
    index: Int,
    onClick: (Int) -> Unit,
) {
    GlideImage(
        modifier = Modifier
            .size(DefaultPhotoSize)
            .background(
                color = Color.Transparent,
                shape = PhotoShape,
            )
            .dormClickable(
                rippleEnabled = true
            ) {
                onClick(index)
            },
        imageModel = photoUrl,
    )
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
                rippleEnabled = true
            ) {
                onClickAddPhoto()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = DormIcon.Plus.drawableId),
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
}