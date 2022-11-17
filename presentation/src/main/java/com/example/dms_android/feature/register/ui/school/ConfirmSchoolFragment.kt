package com.example.dms_android.feature.register.ui.school

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentConfirmSchoolBinding
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.util.invisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel

class ConfirmSchoolFragment : BaseFragment<FragmentConfirmSchoolBinding>(
    R.layout.fragment_confirm_school
) {
    private val vm: ConfirmSchoolViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vm.schoolQuestion()

        repeatOnStarted {
            vm.confirmSchoolEvent.collect { event -> handleEvent(event) }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ConfirmSchoolEvent) {
        when (event) {
            is ConfirmSchoolEvent.CompareSchoolAnswerSuccess -> {
                binding.tvError.invisible()
                binding.btnConfirm.setBackgroundColor(Color.parseColor("#3D8AFF"))
                binding.etReply.setBackgroundResource(R.drawable.register_et_background)
                binding.btnConfirm.isClickable = true
            }

            is ConfirmSchoolEvent.SchoolQuestionSuccess -> {
                binding.tvSchoolQuestion.text = vm.question.toString()
            }

            is ConfirmSchoolEvent.ErrorMessage -> showShortToast(event.message)

            is ConfirmSchoolEvent.CompareSchoolBadRequest -> {
                binding.tvError.text = R.string.LoginBadRequest.toString()
                binding.tvError.setTextColor(R.color.error.toInt())
                binding.btnConfirm.setBackgroundColor(Color.parseColor("#803D8AFF"))
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolNotFound -> {
                binding.tvError.text = R.string.CompareSchoolNotFound.toString()
                binding.tvError.setTextColor(R.color.error.toInt())
                binding.btnConfirm.setBackgroundColor(Color.parseColor("#803D8AFF"))
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolUnauthorized -> {
                binding.tvError.text = R.string.inconsistent_school_reply.toString()
                binding.tvError.setTextColor(R.color.error.toInt())
                binding.btnConfirm.setBackgroundColor(Color.parseColor("#803D8AFF"))
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }
        }
    }

    override fun initView() {
        binding.btnConfirm.isClickable = false

        binding.tvLogin.setOnClickListener {
            TODO("로그인 Activity로 넘어가기")
        }

        binding.etReply.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                vm.schoolAnswer.value = binding.etReply.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                vm.compareSchoolAnswer()
            }
        })


        binding.btnConfirm.setOnClickListener {
            //TODO: 다음 Flow로 이동.
        }
    }
}