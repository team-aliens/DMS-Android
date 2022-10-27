package com.example.dms_android.viewmodel.auth.register

import com.example.auth_domain.usecase.auth.RemoteCheckEmailUseCase
import com.example.auth_domain.usecase.auth.RemoteCompareEmailUseCase
import com.example.dms_android.base.BaseViewModel
import javax.inject.Inject

class EmailValidationViewModel @Inject constructor(
    private val remoteCompareEmailUseCase: RemoteCompareEmailUseCase
): BaseViewModel<> {
}