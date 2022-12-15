package com.example.dms_android.feature.mypage

import com.example.dms_android.base.MviEvent
import com.example.feature_domain.enums.PointType

sealed class MyPageEvent: MviEvent {
    
    data class ChangePointTypeSuccess(val type: PointType): MyPageEvent()
    object UnAuthorizedTokenException: MyPageEvent()
    object CannotConnectException: MyPageEvent()
    object TooManyRequestException: MyPageEvent()
    object InternalServerException: MyPageEvent()
    object UnknownException: MyPageEvent()
}
