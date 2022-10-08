package com.example.design_system.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.design_system.modifier.dormShadow
import com.example.design_system.typography.Body5
import com.example.design_system.typography.Body6
import com.skydoves.landscapist.glide.GlideImage

data class Property(
    val propertyImage: String,
    val title: String,
    val userName: String,
    val profileImage: String,
    val createAt: String,
)

@Composable
fun DormLostPropertyList(
    modifier: Modifier = Modifier,
    propertyList: List<Property>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(propertyList) { index, property ->
            LostProperty(
                property = property,
                index = index,
                onClick = onClick
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
            modifier = Modifier.padding(16.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(LostPropertyImageSize)
                    .background(
                        color = Color.Transparent,
                        shape = LostPropertyShape,
                    ),
                imageModel = property.propertyImage,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Body5(
                    text = property.title,
                    color = DormColor.Gray700,
                )

                Spacer(modifier = Modifier.height(23.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(LostPropertyProfileSize)
                            .background(color = Color.Transparent)
                            .clip(CircleShape),
                        imageModel = property.profileImage,
                        contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Body6(
                        text = property.userName,
                        color = DormColor.Gray600,
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))
                
                Body6(
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
    val fakeImage = "https://media.istockphoto.com/vectors/male-profile-flat-blue-simple-icon-with-long-shadow-vector-id522855255?k=20&m=522855255&s=612x612&w=0&h=fLLvwEbgOmSzk1_jQ0MgDATEVcVOh_kqEe0rqi7aM5A="

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
        onClick =  {},
    )
}