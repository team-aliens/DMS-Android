package team.aliens.dms_android.feature.register.ui.school

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseFragment
import team.aliens.dms_android.feature.MainActivity
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.register.event.school.*
import team.aliens.dms_android.feature.register.ui.email.EnterEmailFragment
import team.aliens.dms_android.util.repeatOnFragmentStarted
import team.aliens.dms_android.viewmodel.auth.register.school.ConfirmSchoolViewModel
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity
import team.aliens.presentation.R
import team.aliens.presentation.databinding.FragmentConfirmSchoolBinding
import java.util.*

@AndroidEntryPoint
class ConfirmSchoolFragment :
    BaseFragment<FragmentConfirmSchoolBinding>(R.layout.fragment_confirm_school) {
    private val confirmSchoolViewModel: ConfirmSchoolViewModel by viewModels()

    private var answer: String = ""
    private var inputData = ""
    private var schoolCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val args = arguments
        inputData = args?.get("schoolId") as String
        schoolCode = args.get("schoolCode") as String

        val uuid = UUID.fromString(inputData)
        confirmSchoolViewModel.schoolId = uuid

        repeatOnFragmentStarted {
            confirmSchoolViewModel.confirmSchoolEvent.collect { event -> handleEvent(event) }
        }

        confirmSchoolViewModel.schoolQuestion(uuid)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun handleEvent(event: ConfirmSchoolEvent) {
        when (event) {
            is FetchSchoolQuestion -> setSchoolQuestionValue(event.schoolConfirmQuestionEntity)
            is CompareSchoolAnswerSuccess -> {
                //Fixme : 추후PR에서 개선 필요
                val registerActive = activity as RegisterActivity

                val bundle = Bundle()
                bundle.putString("schoolId", inputData)
                bundle.putString("answer", answer)
                bundle.putString("schoolCode", schoolCode)

                val fragment = EnterEmailFragment()
                fragment.arguments = bundle

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, fragment).commit()
            }

            MissMatchCompareSchool -> {
                binding.tvError.text = getString(R.string.InconsistentSchoolReply)
                binding.tvError.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.error))
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.isVisible = true
            }
            NotFoundCompareSchool -> {
                binding.tvError.text = getString(R.string.CompareSchoolNotFound)
                binding.tvError.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.error))
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.isVisible = true
            }
        }
    }

    override fun initView() {
        binding.btnConfirm.isClickable = false
        binding.btnConfirm.isEnabled = false

        binding.tvLogin.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

       with(binding.etReply){
           addTextChangedListener {
               answer = it.toString()
               binding.tvError.isVisible = false
               binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_active_btn_background)
               binding.etReply.setBackgroundResource(R.drawable.register_et_background)
               binding.btnConfirm.isClickable = true
               binding.btnConfirm.isEnabled = true
           }
       }

        binding.btnConfirm.setOnClickListener {
            confirmSchoolViewModel.compareSchoolAnswer(answer)
        }
    }

    private fun setSchoolQuestionValue(questionData: SchoolConfirmQuestionEntity) {
        binding.tvSchoolQuestion.text = questionData.question
    }
}