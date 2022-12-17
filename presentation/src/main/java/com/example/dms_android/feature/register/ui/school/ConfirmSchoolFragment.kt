package com.example.dms_android.feature.register.ui.school

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentConfirmSchoolBinding
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.util.invisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ConfirmSchoolFragment : BaseFragment<FragmentConfirmSchoolBinding>(
    R.layout.fragment_confirm_school
) {
    private val confirmSchoolViewModel: ConfirmSchoolViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val args = this.arguments
        val inputData = args?.get("schoolId")

        val uuid = UUID.fromString(inputData.toString())
        confirmSchoolViewModel.schoolId = uuid

        confirmSchoolViewModel.schoolQuestion()

        repeatOnStarted {
            confirmSchoolViewModel.confirmSchoolEvent.collect { event -> handleEvent(event) }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ConfirmSchoolEvent) {
        when (event) {
            is ConfirmSchoolEvent.CompareSchoolAnswerSuccess -> {
                binding.tvError.invisible()
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_background)
                binding.btnConfirm.isClickable = true
            }

            is ConfirmSchoolEvent.SchoolQuestionSuccess -> {
                binding.tvSchoolQuestion.text = confirmSchoolViewModel.question
            }

            is ConfirmSchoolEvent.CompareSchoolBadRequestException -> {
                binding.tvError.text = getString(R.string.BadRequest)
                binding.tvError.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error
                    )
                )
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolNotFoundException -> {
                binding.tvError.text = getString(R.string.CompareSchoolNotFound)
                binding.tvError.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error
                    )
                )
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolUnauthorizedException -> {
                binding.tvError.text = getString(R.string.inconsistent_school_reply)
                binding.tvError.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error
                    )
                )
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.SchoolQuestionInternalServerException -> showShortToast(
                getString(
                    R.string.ServerException
                )
            )

            is ConfirmSchoolEvent.SchoolQuestionTooManyRequestException -> showShortToast(
                getString(
                    R.string.TooManyRequest
                )
            )

            is ConfirmSchoolEvent.CompareSchoolInternalServerException -> showShortToast(getString(R.string.ServerException))
            is ConfirmSchoolEvent.CompareSchoolTooManyRequestException -> showShortToast(getString(R.string.TooManyRequest))
            is ConfirmSchoolEvent.UnknownException -> showShortToast(getString(R.string.UnKnownException))
            is ConfirmSchoolEvent.SchoolQuestionBadRequestException -> showShortToast(getString(R.string.BadRequest))
            is ConfirmSchoolEvent.SchoolQuestionNotFoundException -> showShortToast(getString(R.string.CompareSchoolNotFound))
            is ConfirmSchoolEvent.FetchSchoolQuestion -> setSchoolQuestionValue(event.schoolConfirmQuestionEntity)
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
                confirmSchoolViewModel.schoolAnswer = binding.etReply.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                confirmSchoolViewModel.compareSchoolAnswer()
            }
        })


        binding.btnConfirm.setOnClickListener {
            val mainActive = activity as MainActivity
            mainActive.changeFragment(2)
        }
    }

    private fun setSchoolQuestionValue(questionData: SchoolConfirmQuestionEntity) {
        binding.tvSchoolQuestion.text = questionData.question
    }
}