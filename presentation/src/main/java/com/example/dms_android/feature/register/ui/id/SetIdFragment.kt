package com.example.dms_android.feature.register.ui.id

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.example.dms_android.databinding.FragmentSetIdBinding
import com.example.dms_android.R
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.event.id.SetIdEvent
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment
import com.example.dms_android.util.invisible
import com.example.dms_android.util.repeatOnStarted
import com.example.dms_android.util.visible
import com.example.dms_android.viewmodel.auth.register.id.SetIdViewModel
import com.example.domain.entity.user.ExamineGradeEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class SetIdFragment : BaseFragment<FragmentSetIdBinding>(
    R.layout.fragment_set_id
) {
    private val setIdViewModel: SetIdViewModel by viewModels()

    /* private var nameCheck = false
     private var duplicateIdCheck = false*/

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
            is SetIdEvent.ExamineGradeName -> setNameValue(event.examineGradeEntity)

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
        var id = ""

         if (binding.etClass.text!!.isNotEmpty() && binding.etNumber.text!!.isNotEmpty() && binding.etGrade.text!!.isNotEmpty()) {
             println("excute")
             setIdViewModel.examineGrade(
                 grade = binding.etGrade.text.toString().toInt(),
                 classRoom = binding.etClass.text.toString().toInt(),
                 number = binding.etNumber.text.toString().toInt(),
             )
         }

         binding.btnOKName.setOnClickListener {
             binding.clName.isGone
             //nameCheck = true
         }
        //TODO: next pr
         /*if (nameCheck) {
             binding.etClass.isClickable = false
             binding.etClass.isEnabled = false
             binding.etGrade.isClickable = false
             binding.etGrade.isEnabled = false
             binding.etNumber.isClickable = false
             binding.etNumber.isEnabled = false
         }

         if (nameCheck && duplicateIdCheck) {
             binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
             binding.btnVerificationCode.isClickable = true
             binding.btnVerificationCode.isEnabled = true
         } else {
             binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
             binding.btnVerificationCode.isClickable = false
             binding.btnVerificationCode.isEnabled = false
         }*/

         binding.etId.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
             }

             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 id = p0.toString()
             }

             override fun afterTextChanged(p0: Editable?) {
             }
         })

         binding.btnVerificationCode.setOnClickListener {
             setIdViewModel.duplicateId(id)
         }

         binding.ivBack.setOnClickListener {
             //TODO: 뒤로가는 코드 작성
         }
    }

    private fun setNameValue(examineGradeData: ExamineGradeEntity) {
        binding.clName.isGone
        binding.tvRealName.text = "${examineGradeData.name}님이 맞습니까?"
    }
}