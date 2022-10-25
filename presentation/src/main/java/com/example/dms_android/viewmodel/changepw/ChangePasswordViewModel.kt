package com.example.dms_android.viewmodel.changepw

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.usecase.students.RemoteChangePasswordUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.changepassword.ChangePasswordEvent
import com.example.dms_android.feature.auth.changepassword.ChangePasswordState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: RemoteChangePasswordUseCase
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>() {

    /*
        디자인에서 처음 본인인증할때 아이디만을 사용해서 "아이디 존재 여부(비밀번호 재설정)"이라는 Api로 이에 해당하는 Email를 받습니다.
        그다음 아이디를 입력 받은 다음에 "이메일 검증이라는 Api를 사용하여 이메일과 아이디를 서버에 보낸뒤 이 값들이 정보와 일치하는지 검사합니다."
        검사에서 가능이 뜨게 된다면 "이메일 인증번호 보내기 APi"를 사용해서 사용자 이메일에 이메일을 발송합니다.
        그리고 이메일 인증번호 확인 Api를 사용하여 인증을 완료하고 Students의 비밀번호 재설정 Api를 사용하여 재설정합니다.
    */

    private val parameter =
        ChangePasswordParam(
            accountId = "",
            email = "",
            authCode = "",
            newPassword = "",
        )

    override val initialState: ChangePasswordState
        get() = ChangePasswordState.initial()

    private val _changePasswordEvent = MutableEventFlow<ChangePasswordEvent>()
    val changePasswordEvent = _changePasswordEvent.asEventFlow()

    fun changePassword() {
        viewModelScope.launch {
            kotlin.runCatching {
                changePasswordUseCase.execute(parameter)
            }.onSuccess {
                ChangePasswordEvent.ChangePasswordSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> ChangePasswordEvent.BadRequestException
                    is UnauthorizedException -> ChangePasswordEvent.UnAuthorizedException
                    is NotFoundException -> ChangePasswordEvent.NotFoundException
                    is TooManyRequestException -> ChangePasswordEvent.TooManyRequestException
                    is ServerException -> ChangePasswordEvent.InternalServerException
                    else -> ChangePasswordEvent.UnKnownException
                }
            }
        }
    }

    override fun reduceEvent(oldState: ChangePasswordState, event: ChangePasswordEvent) {
        TODO("Please Impl Here")
    }
}
