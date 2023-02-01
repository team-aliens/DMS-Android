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
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.util.emailValidation
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import java.util.regex.Pattern

@AndroidEntryPoint
class EnterEmailFragment : BaseFragment<FragmentEnterEmailBinding>(
    R.layout.fragment_enter_email
) {
    private var emailAddress: String = ""
    private var inputData = ""
    private var answer: String = ""
    private var schoolCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val args = arguments
        inputData = args?.get("schoolId") as String
        answer = args.get("answer") as String
        schoolCode = args.get("schoolCode") as String

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.btnSendCode.isClickable = false
        binding.btnSendCode.isEnabled = false

        binding.etEnterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailAddress = s.toString()
                if (s!!.isNotEmpty()) {
                    binding.btnSendCode.isClickable = true
                    binding.btnSendCode.isEnabled = true

                    binding.btnSendCode.setBackgroundResource(
                        R.drawable.register_custom_active_btn_background
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.btnSendCode.setOnClickListener {

            checkEmail()
        }
    }

    private fun checkEmail() {
        val email = binding.etEnterEmail.text.toString().trim()
        val p = Pattern.matches(emailValidation, email)
        if (p) {
            val registerActive = activity as RegisterActivity

            val bundle = Bundle()
            val fragment = EmailCertificationFragment()

            bundle.putString("email", emailAddress)
            bundle.putString("schoolId", inputData)
            bundle.putString("answer", answer)
            bundle.putString("schoolCode", schoolCode)
            fragment.arguments = bundle

            registerActive.supportFragmentManager.beginTransaction()
                .replace(R.id.containerRegister, fragment)
                .addToBackStack("EnterEmail")
                .commit()
        } else {
            binding.tvError.visible()
            binding.btnSendCode.isClickable = false
            binding.btnSendCode.isEnabled = false
            binding.btnSendCode.setBackgroundResource(
                R.drawable.register_custom_btn_background
            )
            binding.etEnterEmail.setBackgroundResource(
                R.drawable.register_et_error_background
            )
        }
    }
}
