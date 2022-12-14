package com.example.dms_android.viewmodel.mypage

import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.mypage.MyPageEvent
import com.example.dms_android.feature.mypage.MyPageState
import com.example.feature_domain.usecase.mypage.RemoteMyPageUseCase
import com.example.local_domain.usecase.mypage.LocalMyPageUseCase
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    val remoteMyPageUseCase: RemoteMyPageUseCase,
    val localMyPageUseCase: LocalMyPageUseCase,
): BaseViewModel<MyPageState, MyPageEvent>() {

    override val initialState: MyPageState
        get() = TODO("Not yet implemented")

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        TODO("Not yet implemented")
    }
}
