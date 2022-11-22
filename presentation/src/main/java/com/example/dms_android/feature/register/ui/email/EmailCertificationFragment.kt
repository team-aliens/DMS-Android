package com.example.dms_android.feature.register.ui.email

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentEmailCertificationBinding
import com.example.dms_android.R
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.register.event.email.RegisterEmailEvent
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel

class EmailCertificationFragment : BaseFragment<FragmentEmailCertificationBinding>(
    R.layout.fragment_email_certification
) {
    private val vm: RegisterEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm.sendEmailNumber()

        fiveTimer()

        repeatOnStarted {
            vm.registerEmailEvent.collect { event -> handleEvent(event) }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: RegisterEmailEvent) {
        when (event) {
            is RegisterEmailEvent.CheckEmailSuccess -> {
                val mainActive = activity as MainActivity
                //TODO: 여기에 다음페이지 넘어가는 코드 작성
            }

            is RegisterEmailEvent.SendEmailSuccess -> {
                showShortToast("성공적으로 이메일을 보냈습니다.")
            }

            is RegisterEmailEvent.CheckEmailUnauthorized -> {
                binding.tvDetail.text = R.string.NoSameCode.toString()
                binding.tvDetail.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error
                    )
                )
                binding.btnVerificationCode.setBackgroundResource(
                    R.drawable.register_custom_btn_background
                )
                binding.btnVerificationCode.isClickable = false
            }

            is RegisterEmailEvent.CheckEmailNotFound -> {
                showShortToast(getString(R.string.CertificationInfoNotFound))
            }

            is RegisterEmailEvent.InternalServerException -> {
                showShortToast(getString(R.string.ServerException))
            }

            is RegisterEmailEvent.BadRequestException -> {
                showShortToast(getString(R.string.BadRequest))
            }

            is RegisterEmailEvent.TooManyRequestsException -> {
                showShortToast(getString(R.string.TooManyRequest))
            }

            is RegisterEmailEvent.UnKnownException -> {
                showShortToast(getString(R.string.UnKnownException))
            }
        }
    }

    override fun initView() {

        var temp: String?

        binding.btnVerificationCode.isClickable = false

        binding.tvResendCode.setOnClickListener {
            vm.sendEmailNumber()
            fiveTimer()
        }

        binding.ivBack.setOnClickListener {
            //TODO: finish하는 코드
        }

        binding.etPinEntry.setOnPinEnteredListener() { str ->
            temp = str.toString()
            if (binding.etPinEntry.length() == 6) {
                vm._authCode.value = temp
                binding.btnVerificationCode.setBackgroundResource(
                    R.drawable.register_custom_active_btn_background
                )
                binding.btnVerificationCode.isClickable = true
            } else {
                binding.btnVerificationCode.setBackgroundResource(
                    R.drawable.register_custom_btn_background
                )
                binding.btnVerificationCode.isClickable = false
            }
        }

        binding.btnVerificationCode.setOnClickListener {
            vm.checkEmailCode()
        }
    }

    private fun fiveTimer() {
        object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val num = (millisUntilFinished / 1000).toInt()
                binding.tvMinute.text = (num / 60).toString()
                binding.tvSecond.text = (num % 60).toString()

                if (num == 0) {

                }
            }

            override fun onFinish() {
                binding.tvMinute.text = "0"
                binding.tvSecond.text = "00"
            }
        }.start()
    }
}