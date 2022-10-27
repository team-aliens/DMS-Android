package com.example.dms_android.viewmodel.auth.register

import com.example.auth_domain.usecase.auth.RemoteRequestEmailCodeUseCase
import com.example.dms_android.base.BaseViewModel
import javax.inject.Inject

class RequestEmailViewModel @Inject constructor(
    private val requestEmailCodeUseCase: RemoteRequestEmailCodeUseCase
) : BaseViewModel<> {
}