package com.example.dms_android.feature.register.ui.email

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentEnterEmailBinding
import com.example.dms_android.R
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.util.invisible
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class EnterEmailFragment : BaseFragment<FragmentEnterEmailBinding>(
    R.layout.fragment_enter_email
) {
    private val vm: RegisterEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.btnSendCode.isClickable = false

        fun checkEmail(): Boolean {
            val email = binding.etEnterEmail.text.toString().trim()
            val p = Pattern.matches(emailValidation, email)
            return if (p) {
                binding.tvError.invisible()
                binding.btnSendCode.isClickable = true
                binding.btnSendCode.setBackgroundResource(
                    R.drawable.register_custom_active_btn_background
                )
                binding.etEnterEmail.setBackgroundResource(
                    R.drawable.register_et_background
                )
                true
            } else {
                binding.tvError.visible()
                binding.btnSendCode.isClickable = false
                binding.btnSendCode.setBackgroundResource(
                    R.drawable.register_custom_btn_background
                )
                binding.etEnterEmail.setBackgroundResource(
                    R.drawable.register_et_error_background
                )
                false
            }
        }

        binding.etEnterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnSendCode.setOnClickListener {
            val mainActive = activity as MainActivity
            vm._email.value = binding.etEnterEmail.toString()
            mainActive.changeFragment(3)
        }
    }
}