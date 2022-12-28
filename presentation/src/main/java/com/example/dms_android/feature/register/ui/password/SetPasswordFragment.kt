package com.example.dms_android.feature.register.ui.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentSetPasswordBinding
import com.example.dms_android.R
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.id.SetIdFragment
import com.example.dms_android.feature.register.ui.last.SetProfileImageFragment
import com.example.dms_android.util.invisible
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPasswordFragment : BaseFragment<FragmentSetPasswordBinding>(
    R.layout.fragment_set_password
) {
    private val signUpViewModel: SignUpViewModel by viewModels()

    private var pwd = ""
    private var rePwd = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.btnVerificationCode.isEnabled = false

        activeButtonLogic()

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction()
                .remove(SetPasswordFragment())
                .commit()
        }

        binding.btnVerificationCode.setOnClickListener {
            if (pwd == rePwd) {
                val registerActive = activity as RegisterActivity

                signUpViewModel.password = pwd

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, SetProfileImageFragment())
                    .addToBackStack("SetPassword")
                    .commit()
            } else {
                binding.etReEnterPassword.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isEnabled = false
            }
        }
    }

    private fun activeButtonLogic() {
        binding.etEnterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                pwd = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.etEnterPassword.text!!.isNotEmpty() && binding.etReEnterPassword.text!!.isNotEmpty()){
                    binding.btnVerificationCode.isEnabled = true
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                }
            }
        })

        binding.etReEnterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rePwd = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.etEnterPassword.text!!.isNotEmpty() && binding.etReEnterPassword.text!!.isNotEmpty()){
                    binding.btnVerificationCode.isEnabled = true
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                }
            }
        })
    }
}