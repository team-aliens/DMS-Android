package com.example.dms_android.viewmodel.auth.register.last

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentPolicyBinding
import com.example.dms_android.feature.register.event.SignUpEvent
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.viewmodel.auth.register.last.dialog.GoLoginDialog

class PolicyFragment : BaseFragment<FragmentPolicyBinding>(
    R.layout.fragment_policy
) {
    private val signUpViewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                GoLoginDialog(this, onYesClick = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }).callDialog()
            }

            SignUpEvent.TooManyRequestsException -> showShortToast(getString(R.string.TooManyRequest))
            SignUpEvent.UnAuthorizedException -> showShortToast(getString(R.string.SignUpUnAuthorized))
            SignUpEvent.UnKnownException -> showShortToast(getString(R.string.UnKnownException))
        }
    }

    override fun initView() {
        binding.webView.loadUrl("https://team-aliens-webview.dsm-dms.com/sign-up-policy")
        binding.btnVerificationCode.isClickable = binding.cbCheckAllPolicy.isChecked

        if (binding.cbCheckAllPolicy.isChecked) {
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
            binding.btnVerificationCode.isClickable = true
        } else {
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
            binding.btnVerificationCode.isClickable = false
        }

        binding.btnVerificationCode.setOnClickListener {
            signUpViewModel.signUp()
        }
    }
}