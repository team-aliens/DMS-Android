package team.aliens.dms_android.feature.register.ui.school

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseFragment
import team.aliens.dms_android.feature.MainActivity
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.register.event.school.ConfirmSchoolEvent
import team.aliens.dms_android.feature.register.ui.email.EnterEmailFragment
import team.aliens.dms_android.util.invisible
import team.aliens.dms_android.util.repeatOnStarted
import team.aliens.dms_android.util.visible
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
                bundle.putString("answer", answer)
                bundle.putString("schoolCode", schoolCode)

                val fragment = EnterEmailFragment()
                fragment.arguments = bundle

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, fragment).commit()
            }

            is ConfirmSchoolEvent.CompareSchoolBadRequestException -> {
                binding.tvError.text = getString(R.string.BadRequest)
                binding.tvError.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.error))
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolNotFoundException -> {
                binding.tvError.text = getString(R.string.CompareSchoolNotFound)
                binding.tvError.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.error))
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.CompareSchoolUnauthorizedException -> {
                binding.tvError.text = getString(R.string.InconsistentSchoolReply)
                binding.tvError.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.error))
                binding.btnConfirm.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.etReply.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.visible()
            }

            is ConfirmSchoolEvent.SchoolQuestionInternalServerException -> showShortToast(getString(
                R.string.ServerException))

            is ConfirmSchoolEvent.SchoolQuestionTooManyRequestException -> showShortToast(getString(
                R.string.TooManyRequest))

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
            confirmSchoolViewModel.compareSchoolAnswer(answer)
        }
    }

    private fun setSchoolQuestionValue(questionData: SchoolConfirmQuestionEntity) {
        binding.tvSchoolQuestion.text = questionData.question
    }
}