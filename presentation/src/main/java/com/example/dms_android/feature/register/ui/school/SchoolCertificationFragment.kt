package com.example.dms_android.feature.register.ui.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    private val examineSchoolCodeViewModel: ExamineSchoolCodeViewModel by viewModels()
    private val confirmSchoolViewModel: ConfirmSchoolViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repeatOnStarted {
            examineSchoolCodeViewModel.examineSchoolCodeEvent.collect { event -> handleEvent(event) }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ExamineSchoolCodeEvent) = when (event) {
        is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
            val mainActive = activity as MainActivity
            confirmSchoolViewModel.schoolId = examineSchoolCodeViewModel.schoolId
            mainActive.changeFragment(1)
        }

        is ExamineSchoolCodeEvent.BadRequestException -> {
            showShortToast(R.string.BadRequest.toString())
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
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
            binding.btnVerificationCode.isClickable = false
        }

        is ExamineSchoolCodeEvent.UnknownException -> {
            showShortToast(R.string.UnKnownException.toString())
        }
    }

    override fun initView() {
        var temp: String? = null

        binding.etPinEntry.setOnPinEnteredListener() { str ->
            temp = str.toString()
            if (binding.etPinEntry.length() == 8) {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                binding.btnVerificationCode.isClickable = true
            } else {
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isClickable = false
            }
        }

        binding.btnVerificationCode.setOnClickListener {
            temp?.let {
                examineSchoolCodeViewModel.examineSchoolCode(it)
            }
        }
    }
}
