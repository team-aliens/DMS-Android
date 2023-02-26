package team.aliens.dms_android.feature.register.ui.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseFragment
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.register.event.school.ExamineSchoolCodeSuccess
import team.aliens.dms_android.feature.register.event.school.MissMatchSchoolCode
import team.aliens.dms_android.util.repeatOnFragmentStarted
import team.aliens.dms_android.util.repeatOnStarted
import team.aliens.dms_android.viewmodel.auth.register.school.ExamineSchoolCodeViewModel
import team.aliens.presentation.R
import team.aliens.presentation.databinding.FragmentSchoolCertificationBinding

@AndroidEntryPoint
class SchoolCertificationFragment :
    BaseFragment<FragmentSchoolCertificationBinding>(R.layout.fragment_school_certification) {
    private val examineSchoolCodeViewModel: ExamineSchoolCodeViewModel by viewModels()
    private var temp: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        callNetwork()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun callNetwork() {
        repeatOnFragmentStarted {
            examineSchoolCodeViewModel.examineSchoolCodeEvent.collect {
                when (it) {
                    is ExamineSchoolCodeSuccess -> {
                        //Fixme : 추후PR에서 개선 필요
                        val registerActive = activity as RegisterActivity

                        val bundle = Bundle()
                        bundle.putString("schoolId", examineSchoolCodeViewModel.schoolId.toString())
                        bundle.putString("schoolCode", temp)

                        val fragment = ConfirmSchoolFragment()
                        fragment.arguments = bundle
                        registerActive.supportFragmentManager.beginTransaction()
                            .replace(R.id.containerRegister, fragment).commit()
                    }

                    is MissMatchSchoolCode -> {
                        binding.tvDetail.text = getString(R.string.SchoolUnAuthorized)
                        binding.tvDetail.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.error
                            )
                        )
                        binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                        binding.btnVerificationCode.isClickable = false
                    }
                }
            }
        }
    }

    override fun initView() {
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
