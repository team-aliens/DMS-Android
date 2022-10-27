package com.example.dms_android.viewmodel.auth.register

import com.example.auth_domain.usecase.auth.duplicateCheckIdUseCase
import com.example.dms_android.base.BaseViewModel
import javax.inject.Inject

class DuplicateCheckIdViewModel @Inject constructor(
    private val duplicateCheckIdUseCase: duplicateCheckIdUseCase
) : BaseViewModel<> {
}