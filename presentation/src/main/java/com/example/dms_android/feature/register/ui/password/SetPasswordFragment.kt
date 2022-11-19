package com.example.dms_android.feature.register.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentSetPasswordBinding
import com.example.dms_android.R
import com.example.dms_android.util.invisible
import com.example.dms_android.util.visible

class SetPasswordFragment : BaseFragment<FragmentSetPasswordBinding>(
    R.layout.fragment_set_password
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {

        binding.btnVerificationCode.isClickable =
            binding.etEnterPassword.length() > 1 && binding.etReEnterPassword.length() > 1

        if (binding.etEnterPassword != binding.etReEnterPassword) {
            binding.etReEnterPassword.setBackgroundResource(R.drawable.register_et_error_background)
            binding.tvError.visible()
        } else {
            binding.etReEnterPassword.setBackgroundResource(R.drawable.register_et_background)
            binding.tvError.invisible()
        }

        binding.ivBack.setOnClickListener {
            //TODO: back code
        }

        binding.btnVerificationCode.setOnClickListener {
            //TODO: 다음 화면으로 가는 코드 작성, 회원가입 뷰모델 만들어지면 password 넣어야됨.....
        }
    }
}