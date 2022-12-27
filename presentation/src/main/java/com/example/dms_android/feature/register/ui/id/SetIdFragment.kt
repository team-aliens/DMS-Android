package com.example.dms_android.feature.register.ui.id

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.dms_android.databinding.FragmentSetIdBinding
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.event.id.SetIdEvent
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment
import com.example.dms_android.util.isVisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.viewmodel.auth.register.id.SetIdViewModel
import com.example.domain.entity.user.ExamineGradeEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class SetIdFragment : BaseFragment<FragmentSetIdBinding>(
    R.layout.fragment_set_id
) {
    private val setIdViewModel: SetIdViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args = arguments
        val inputData = args!!.get("schoolId")

        val uuid = UUID.fromString(inputData.toString())
        setIdViewModel.schoolId = uuid

        repeatOnStarted {
            setIdViewModel.examineGradeEvent.collect { event -> handleEvent(event) }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: SetIdEvent) {
        when (event) {
            is SetIdEvent.ExamineGradeName -> {
                binding.etGrade.setBackgroundResource(R.drawable.register_et_background)
                binding.etClass.setBackgroundResource(R.drawable.register_et_background)
                binding.etNumber.setBackgroundResource(R.drawable.register_et_background)
                binding.clName.visibility = View.VISIBLE
                setNameValue(event.examineGradeEntity)
            }

            is SetIdEvent.ExamineGradeConflictException -> {
                showShortToast(getString(R.string.CheckGrade))
                binding.etGrade.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etClass.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etNumber.setBackgroundResource(R.drawable.register_et_error_background)
            }

            is SetIdEvent.ExamineGradeBadRequestException -> {
                showShortToast(getString(R.string.BadRequest))
            }

            is SetIdEvent.ExamineGradeNotFoundException -> {
                showShortToast(getString(R.string.CheckGrade))
                binding.etGrade.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etClass.setBackgroundResource(R.drawable.register_et_error_background)
                binding.etNumber.setBackgroundResource(R.drawable.register_et_error_background)
            }

            is SetIdEvent.ExamineGradeInterServerException -> showShortToast(getString(R.string.ServerException))
            is SetIdEvent.ExamineGradeTooManyRequestException -> showShortToast(getString(R.string.TooManyRequest))

            is SetIdEvent.DuplicateIdSuccess -> {
                val registerActive = activity as RegisterActivity

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, SetPasswordFragment())
                    .addToBackStack("SetId")
                    .commit()
            }

            is SetIdEvent.DuplicateIdConflictException -> {
                binding.etId.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is SetIdEvent.DuplicateIdBadRequestException -> showShortToast(getString(R.string.BadRequest))
            is SetIdEvent.DuplicateIdInterServerException -> showShortToast(getString(R.string.ServerException))
            is SetIdEvent.DuplicateIdTooManyRequestException -> showShortToast(getString(R.string.TooManyRequest))

            is SetIdEvent.UnknownException -> showShortToast(getString(R.string.UnKnownException))
        }
    }

    override fun initView() {
        binding.btnVerificationCode.isClickable = false
        binding.btnVerificationCode.isEnabled = false
        binding.etId.isEnabled = false

        var id = ""

        checkNameLogic()

        binding.btnOKName.setOnClickListener {
            binding.clName.visibility = View.GONE
            binding.etId.isEnabled = true

            binding.etNumber.isEnabled = false
            binding.etGrade.isEnabled = false
            binding.etClass.isEnabled = false
        }

        binding.etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                id = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etId.text!!.isNotEmpty()) {
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                    binding.btnVerificationCode.isClickable = true
                    binding.btnVerificationCode.isEnabled = true
                } else {
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                    binding.btnVerificationCode.isClickable = false
                    binding.btnVerificationCode.isEnabled = false
                }
            }
        })

        binding.btnVerificationCode.setOnClickListener {
            signUpViewModel.classRoom = binding.etClass.text.toString().toInt()
            signUpViewModel.grade = binding.etGrade.text.toString().toInt()
            signUpViewModel.number = binding.etNumber.text.toString().toInt()
            signUpViewModel.accountId = id

            setIdViewModel.duplicateId(id)
        }

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction()
                .remove(SetIdFragment())
                .commit()
        }
    }
    private fun checkNameLogic() {
        binding.etGrade.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etClass.text!!.isNotEmpty() && binding.etNumber.text!!.isNotEmpty() && binding.etGrade.text!!.isNotEmpty()) {
                    setIdViewModel.examineGrade(
                        grade = binding.etGrade.text.toString().toInt(),
                        classRoom = binding.etClass.text.toString().toInt(),
                        number = binding.etNumber.text.toString().toInt(),
                    )
                }
            }
        })

        binding.etClass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etClass.text!!.isNotEmpty() && binding.etNumber.text!!.isNotEmpty() && binding.etGrade.text!!.isNotEmpty()) {
                    setIdViewModel.examineGrade(
                        grade = binding.etGrade.text.toString().toInt(),
                        classRoom = binding.etClass.text.toString().toInt(),
                        number = binding.etNumber.text.toString().toInt(),
                    )
                }
            }
        })

        binding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etClass.text!!.isNotEmpty() && binding.etNumber.text!!.isNotEmpty() && binding.etGrade.text!!.isNotEmpty()) {
                    setIdViewModel.examineGrade(
                        grade = binding.etGrade.text.toString().toInt(),
                        classRoom = binding.etClass.text.toString().toInt(),
                        number = binding.etNumber.text.toString().toInt(),
                    )
                }
            }
        })
    }

    private fun setNameValue(examineGradeData: ExamineGradeEntity) {
        binding.tvRealName.text = "${examineGradeData.name}님이 맞습니까?"
    }
}