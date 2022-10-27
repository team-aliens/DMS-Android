package com.example.dms_android.viewmodel.auth.register

import com.example.auth_domain.usecase.auth.RemoteCheckEmailUseCase
import com.example.dms_android.base.BaseViewModel
import javax.inject.Inject

class CheckEmailCodeViewModel @Inject constructor(
    private val checkEmailUseCase: RemoteCheckEmailUseCase
) : BaseViewModel<>{
}