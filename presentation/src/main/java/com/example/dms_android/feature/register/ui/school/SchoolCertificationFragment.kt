package com.example.dms_android.feature.register.ui.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        repeatOnStarted {
            examineSchoolCodeViewModel.examineSchoolCodeEvent.collect { event -> handleEvent(event) }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun handleEvent(event: ExamineSchoolCodeEvent) = when (event) {
        is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
            val mainActive = activity as MainActivity

            val bundle = Bundle()
            bundle.putString("schoolId", examineSchoolCodeViewModel.schoolId.toString())

            val fragment = ConfirmSchoolFragment()
            fragment.arguments = bundle
            mainActive.supportFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, fragment).commit()
        }

        is ExamineSchoolCodeEvent.BadRequestException -> {
            showShortToast(getString(R.string.BadRequest))
        }

        is ExamineSchoolCodeEvent.InternalServerException -> {
            showShortToast(getString(R.string.ServerException))
        }

        is ExamineSchoolCodeEvent.TooManyRequestException -> {
            showShortToast(getString(R.string.TooManyRequest))
        }

        is ExamineSchoolCodeEvent.UnAuthorizedException -> {
            binding.tvDetail.text = getString(R.string.SchoolUnAuthorized)
            binding.tvDetail.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
            binding.btnVerificationCode.isClickable = false
        }

        is ExamineSchoolCodeEvent.UnknownException -> {
            showShortToast(getString(R.string.UnKnownException))
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
