package com.example.dms_android.feature.register.ui.school

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.alimuzaffar.lib.pin.PinEntryEditText.OnPinEnteredListener
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentSchoolCertificationBinding
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel
import com.example.dms_android.viewmodel.auth.register.school.ExamineSchoolCodeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SchoolCertificationFragment : BaseFragment<FragmentSchoolCertificationBinding>(
    R.layout.fragment_school_certification
) {
    private val vmExamineSchoolCode: ExamineSchoolCodeViewModel by viewModels()
    private val vmSchoolQuestion: ConfirmSchoolViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repeatOnStarted {
            vmExamineSchoolCode.examineSchoolCodeEvent.collect { event -> handleEvent(event) }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ExamineSchoolCodeEvent) = when (event) {
        is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
            val mainActive = activity as MainActivity
            vmSchoolQuestion.inputSchoolId = vmExamineSchoolCode.schoolId
            mainActive.changeFragment(1)
        }

        is ExamineSchoolCodeEvent.BadRequestException -> {
            showShortToast(R.string.LoginBadRequest.toString())
        }

        is ExamineSchoolCodeEvent.InternalServerException -> {
            showShortToast(R.string.ServerException.toString())
        }

        is ExamineSchoolCodeEvent.TooManyRequestException -> {
            showShortToast(R.string.TooManyRequest.toString())
        }

        is ExamineSchoolCodeEvent.UnAuthorizedException -> {
            binding.tvDetail.text = R.string.SchoolUnAuthorized.toString()
            binding.tvDetail.setTextColor(R.color.error.toInt())
            binding.btnVerificationCode.setBackgroundColor(Color.parseColor("#803D8AFF"))
            binding.btnVerificationCode.isClickable = false
        }

        is ExamineSchoolCodeEvent.UnknownException -> {
            showShortToast(R.string.UnKnownException.toString())
        }
    }

    override fun initView() {

        //TODO : 구현 필요 (시간을 너무 많이 써서 딴 거부터 조짐)
        binding.etPinEntry.setOnPinEnteredListener() {
            if (binding.etPinEntry.length() == 8) {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                binding.btnVerificationCode.isClickable = true
            } else {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isClickable = false
            }
        }

            binding.btnVerificationCode.setOnClickListener {
                binding.etPinEntry.setOnPinEnteredListener(OnPinEnteredListener { str ->
                    vmExamineSchoolCode.examineSchoolCode(str.toString())
                })
            }

    }
}
