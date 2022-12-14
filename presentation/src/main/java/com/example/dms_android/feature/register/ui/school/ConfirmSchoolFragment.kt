package com.example.dms_android.feature.register.ui.school

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentConfirmSchoolBinding
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.feature.register.ui.email.EnterEmailFragment
import com.example.dms_android.util.invisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel
import com.example.domain.entity.user.SchoolConfirmQuestionEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ConfirmSchoolFragment : BaseFragment<FragmentConfirmSchoolBinding>(
    R.layout.fragment_confirm_school
) {
    private val confirmSchoolViewModel: ConfirmSchoolViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()

    private var answer: String = ""
    private var inputData = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val args = arguments
        inputData = args?.get("schoolId") as String

        val uuid = UUID.fromString(inputData.toString())
        confirmSchoolViewModel.schoolId = uuid

        repeatOnStarted {
            confirmSchoolViewModel.confirmSchoolEvent.collect { event -> handleEvent(event) }
        }

        confirmSchoolViewModel.schoolQuestion(uuid)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ConfirmSchoolEvent) {
        when (event) {
            is ConfirmSchoolEvent.CompareSchoolAnswerSuccess -> {
                val registerActive = activity as RegisterActivity

                val bundle = Bundle()
                bundle.putString("schoolId", inputData)

                val fragment = EnterEmailFragment()
                fragment.arguments = bundle

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, fragment)
                    .commit()
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

            is ConfirmSchoolEvent.SchoolQuestionBadRequestException -> showShortToast(getString(R.string.BadRequest))
            is ConfirmSchoolEvent.SchoolQuestionNotFoundException -> showShortToast(getString(R.string.CompareSchoolNotFound))
            is ConfirmSchoolEvent.FetchSchoolQuestion -> setSchoolQuestionValue(event.schoolConfirmQuestionEntity)
            is ConfirmSchoolEvent.UnknownException -> showShortToast(getString(R.string.UnKnownException))
        }
    }

    override fun initView() {
        binding.btnConfirm.isClickable = false
        binding.btnConfirm.isEnabled = false

        binding.tvLogin.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        binding.etReply.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                answer = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.isNotEmpty()) {
                    binding.tvError.invisible()
                    binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                    binding.etReply.setBackgroundResource(R.drawable.register_et_background)
                    binding.btnConfirm.isClickable = true
                    binding.btnConfirm.isEnabled = true
                }
            }
        })

        binding.btnConfirm.setOnClickListener {
            signUpViewModel.schoolAnswer = answer
            confirmSchoolViewModel.compareSchoolAnswer(answer)
        }
    }

    private fun setSchoolQuestionValue(questionData: SchoolConfirmQuestionEntity) {
        binding.tvSchoolQuestion.text = questionData.question
    }
}