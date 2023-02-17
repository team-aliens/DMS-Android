package team.aliens.dms_android.feature.register.ui.email

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseFragment
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.feature.register.ui.id.SetIdFragment
import team.aliens.dms_android.util.repeatOnStarted
import team.aliens.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import team.aliens.presentation.R
import team.aliens.presentation.databinding.FragmentEmailCertificationBinding

@AndroidEntryPoint
class EmailCertificationFragment :
    BaseFragment<FragmentEmailCertificationBinding>(R.layout.fragment_email_certification) {
    private val registerEmailViewModel: RegisterEmailViewModel by viewModels()

    private var email: String = ""
    private var inputSchoolIdData: String = ""
    private var temp: String = ""
    private var answer: String = ""
    private var schoolCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val args = arguments
        inputSchoolIdData = args?.get("schoolId") as String
        email = args.get("email") as String
        answer = args.get("answer") as String
        schoolCode = args.get("schoolCode") as String

        registerEmailViewModel.requestEmailCode(email)

        repeatOnStarted {
            registerEmailViewModel.registerEmailEvent.collect { event -> handleEvent(event) }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: RegisterEmailEvent) {
        when (event) {
            is RegisterEmailEvent.CheckEmailSuccess -> {
                val registerActive = activity as RegisterActivity
                val bundle = Bundle()
                bundle.putString("schoolId", inputSchoolIdData)
                bundle.putString("email", email)
                bundle.putString("answer", answer)
                bundle.putString("schoolCode", schoolCode)
                bundle.putString("authCode", temp)

                val fragment = SetIdFragment()
                fragment.arguments = bundle

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, fragment).addToBackStack("EmailCertification")
                    .commit()
            }

            is RegisterEmailEvent.SendEmailSuccess -> {
                showShortToast(getString(R.string.SendSuccess))
                fiveTimer()
            }

            is RegisterEmailEvent.CheckEmailUnauthorized -> {
                binding.tvDetail.text = getString(R.string.NoSameCode)
                binding.tvDetail.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error,
                    )
                )
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
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
                binding.tvDetail.text = getString(R.string.EmailTooManyRequest)
                binding.tvDetail.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error,
                    )
                )
                showShortToast(getString(R.string.TooManyRequest))
            }

            is RegisterEmailEvent.UnKnownException -> {
                showShortToast(getString(R.string.UnKnownException))
            }
        }
    }

    override fun initView() {

        binding.btnVerificationCode.isClickable = false

        binding.tvResendCode.setOnClickListener {
            registerEmailViewModel.requestEmailCode(email)
            fiveTimer()
        }

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction()
                .remove(EmailCertificationFragment()).commit()
        }

        binding.etPinEntry.setOnPinEnteredListener() { str ->
            temp = str.toString()
            if (binding.etPinEntry.length() == 6) {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                binding.btnVerificationCode.isClickable = true
                binding.btnVerificationCode.isEnabled = true
            } else {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isClickable = false
                binding.btnVerificationCode.isEnabled = false
            }
        }

        binding.btnVerificationCode.setOnClickListener {
            registerEmailViewModel.checkEmailCode(email = email, authCode = temp)
        }
    }

    private fun fiveTimer() {
        object : CountDownTimer(175000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val num = (millisUntilFinished / 1000).toInt()
                binding.tvMinute.text = (num / 60).toString()
                binding.tvSecond.text = (num % 60).toString()

                if (num == 0) {
                    binding.tvDetail.text = getString(R.string.AuthenticationTimeout)
                    binding.tvDetail.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.error,
                        )
                    )
                    binding.btnVerificationCode.isClickable = false
                    binding.btnVerificationCode.isEnabled = false
                }
            }

            override fun onFinish() {
                binding.tvMinute.text = "0"
                binding.tvSecond.text = "00"
            }
        }.start()
    }
}