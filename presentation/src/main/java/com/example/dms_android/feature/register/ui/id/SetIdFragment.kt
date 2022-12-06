package com.example.dms_android.feature.register.ui.id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.example.dms_android.databinding.FragmentSetIdBinding
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.register.event.id.SetIdEvent
import com.example.dms_android.util.invisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.id.SetIdViewModel

class SetIdFragment : BaseFragment<FragmentSetIdBinding>(
    R.layout.fragment_set_id
) {
    private val vm: SetIdViewModel by viewModels()

    private var nameCheck = false
    private var duplicateIdCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        repeatOnStarted {
            vm.examineGradeEvent.collect { event -> handleEvent(event) }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: SetIdEvent) {
        when (event) {
            is SetIdEvent.ExamineGradeSuccess -> {
                binding.clName.visible()
                binding.tvRealName.text = "${vm.name}님이 맞습니까?"
            }

            is SetIdEvent.DuplicateIdSuccess -> {
                duplicateIdCheck = true
                binding.etId.setBackgroundResource(R.drawable.register_et_background)
                binding.tvError.invisible()
            }

            is SetIdEvent.ExamineConflict -> {
                showShortToast(R.string.CheckGrade.toString())
                binding.etGrade.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etClass.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etNumber.setBackgroundResource(R.drawable.register_et_error_background)
            }

            is SetIdEvent.ExamineGradeBadRequest -> {
                showShortToast(R.string.LoginBadRequest.toString())
            }

            is SetIdEvent.ExamineGradeNotFound -> {
                showShortToast(R.string.CheckGrade.toString())
                binding.etGrade.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etClass.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etNumber.setBackgroundResource(R.drawable.register_et_error_background)
            }

            is SetIdEvent.DuplicateIdConflict -> {
                binding.etId.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is SetIdEvent.ErrorMessage -> showShortToast(event.message)
        }
    }

    override fun initView() {
        binding.btnVerificationCode.isClickable = false

        if (binding.etClass.length() > 1 && binding.etNumber.length() > 1 && binding.etGrade.length() > 1) {
            vm.grade = binding.etGrade.toString().toInt()
            vm.classRoom = binding.etClass.toString().toInt()
            vm.number = binding.etNumber.toString().toInt()

            vm.examineGrade()
        }

        binding.btnOKName.setOnClickListener {
            binding.clName.isGone
            nameCheck = true
        }

        if (nameCheck) {
            binding.etClass.isClickable = false
            binding.etClass.isFocusable = false
            binding.etGrade.isClickable = false
            binding.etGrade.isFocusable = false
            binding.etNumber.isClickable = false
            binding.etNumber.isFocusable = false
        }

        if (nameCheck && duplicateIdCheck) {
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
            binding.btnVerificationCode.isClickable = true
        } else {
            binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
            binding.btnVerificationCode.isClickable = false
        }

        binding.btnVerificationCode.setOnClickListener {
            val mainActive = activity as MainActivity
            mainActive.changeFragment(5)
        }

        binding.ivBack.setOnClickListener {
            //TODO: 뒤로가는 코드 작성
        }
    }
}