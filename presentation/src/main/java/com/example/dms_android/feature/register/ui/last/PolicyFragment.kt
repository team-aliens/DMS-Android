package com.example.dms_android.feature.register.ui.last

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentPolicyBinding
import com.example.dms_android.feature.HomeActivity
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.event.SignUpEvent
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.feature.register.ui.last.dialog.GoLoginDialog
import com.example.domain.param.RegisterParam
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyFragment : BaseFragment<FragmentPolicyBinding>(
    R.layout.fragment_policy
) {
    private val signUpViewModel: SignUpViewModel by viewModels()

    private var pwd = ""
    private var email: String = ""
    private var authCode: String = ""
    private var answer: String = ""
    private var schoolCode: String = ""
    private var id = ""
    private var grade = 0
    private var number = 0
    private var classRoom = 0
    private var profileImageUrl : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args = arguments
        email = args?.get("email") as String
        answer = args.get("answer") as String
        schoolCode = args.get("schoolCode") as String
        authCode = args.get("authCode") as String
        id = args.get("accountId") as String
        grade = args.getInt("grade")
        number = args.getInt("number")
        classRoom = args.getInt("classRoom")
        pwd = args.get("password") as String

        repeatOnStarted {
            signUpViewModel.signUpViewEvent.collect { event -> handleEvent(event) }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.BadRequestException -> showShortToast(getString(R.string.BadRequest))
            SignUpEvent.ConflictException -> showShortToast(getString(R.string.SignUpConflict))
            SignUpEvent.InternalServerException -> showShortToast(getString(R.string.ServerException))
            SignUpEvent.NotFoundException -> showShortToast(getString(R.string.NotFound))
            SignUpEvent.SignUpSuccess -> {
                GoLoginDialog(requireContext(), onYesClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }).callDialog()
            }

            SignUpEvent.TooManyRequestsException -> showShortToast(getString(R.string.TooManyRequest))
            SignUpEvent.UnAuthorizedException -> showShortToast(getString(R.string.SignUpUnAuthorized))
            SignUpEvent.UnKnownException -> showShortToast(getString(R.string.UnKnownException))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://team-aliens-webview.dsm-dms.com/sign-up-policy")
        binding.btnVerificationCode.isEnabled = false
        binding.cbCheckAllPolicy.isChecked = false

        binding.cbCheckAllPolicy.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                binding.btnVerificationCode.isEnabled = true
            } else {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isEnabled = false
            }
        }

        binding.btnVerificationCode.setOnClickListener {
            signUpViewModel.signUp(
                RegisterParam(
                    schoolCode = schoolCode,
                    schoolAnswer = answer,
                    email = email,
                    authCode = authCode,
                    grade = grade,
                    classRoom = classRoom,
                    number = number,
                    accountId = id,
                    password = pwd,
                    profileImageUrl = profileImageUrl,
                )
            )
        }

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction()
                .remove(PolicyFragment())
                .commit()
        }
    }
}