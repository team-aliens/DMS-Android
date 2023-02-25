package team.aliens.dms_android.feature.register.ui.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseFragment
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.register.ui.last.SetProfileImageFragment
import team.aliens.presentation.R
import team.aliens.presentation.databinding.FragmentSetPasswordBinding

@AndroidEntryPoint
class SetPasswordFragment :
    BaseFragment<FragmentSetPasswordBinding>(R.layout.fragment_set_password) {
    private var pwd = ""
    private var rePwd = ""
    private var email: String = ""
    private var authCode: String = ""
    private var answer: String = ""
    private var schoolCode: String = ""
    private var id = ""
    private var grade = 0
    private var number = 0
    private var classRoom = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args = arguments
        email = args?.get("email") as String
        answer = args.get("answer") as String
        schoolCode = args.get("schoolCode") as String
        authCode = args.get("authCode") as String
        id = args.get("accountId") as String
        grade = args.getInt("grade")
        number = args.getInt("number")
        classRoom = args.getInt("classRoom")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.btnVerificationCode.isEnabled = false

        activeButtonLogic()

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction().remove(SetPasswordFragment())
                .commit()
        }

        binding.btnVerificationCode.setOnClickListener {
            if (pwd == rePwd) {
                val registerActive = activity as RegisterActivity

                val bundle = Bundle()
                val fragment = SetProfileImageFragment()

                bundle.putString("email", email)
                bundle.putString("answer", answer)
                bundle.putString("schoolCode", schoolCode)
                bundle.putString("authCode", authCode)
                bundle.putInt("grade", grade)
                bundle.putInt("classRoom", classRoom)
                bundle.putInt("number", number)
                bundle.putString("accountId", id)
                bundle.putString("password", pwd)

                fragment.arguments = bundle

                registerActive.supportFragmentManager.beginTransaction()
                    .replace(R.id.containerRegister, fragment).addToBackStack("SetPassword")
                    .commit()
            } else {
                binding.etReEnterPassword.setBackgroundResource(R.drawable.register_et_error_background)
                binding.tvError.isVisible = true
                binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_btn_background)
                binding.btnVerificationCode.isEnabled = false
            }
        }
    }

    private fun activeButtonLogic() {
        binding.etEnterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                pwd = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etEnterPassword.text!!.isNotEmpty() && binding.etReEnterPassword.text!!.isNotEmpty()) {
                    binding.btnVerificationCode.isEnabled = true
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                }
            }
        })

        binding.etReEnterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rePwd = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etEnterPassword.text!!.isEmpty() && binding.etReEnterPassword.text!!.isNotEmpty()) {
                    binding.btnVerificationCode.isEnabled = true
                    binding.btnVerificationCode.setBackgroundResource(R.drawable.register_custom_active_btn_background)
                }
            }
        })
    }
}