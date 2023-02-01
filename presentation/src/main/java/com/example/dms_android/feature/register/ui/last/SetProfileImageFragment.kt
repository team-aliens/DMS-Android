package com.example.dms_android.feature.register.ui.last

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentSetProfileImageBinding
import com.example.dms_android.R
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment

class SetProfileImageFragment : BaseFragment<FragmentSetProfileImageBinding>(
    R.layout.fragment_set_profile_image
) {
    private var pwd = ""
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
        savedInstanceState: Bundle?
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
        pwd = args.get("password") as String

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {

        binding.ivBack.setOnClickListener {
            val registerActive = activity as RegisterActivity
            registerActive.supportFragmentManager.beginTransaction()
                .remove(SetProfileImageFragment())
                .commit()
        }

        binding.tvSetLater.setOnClickListener {
            val registerActive = activity as RegisterActivity

            val bundle = Bundle()
            val fragment = PolicyFragment()

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
                .replace(R.id.containerRegister, fragment)
                .commit()
        }

        binding.btnAddImage.setOnClickListener {
            //TODO: 사진 구현 필요
        }
    }
}