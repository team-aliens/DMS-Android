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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

            registerActive.supportFragmentManager.beginTransaction()
                .replace(R.id.containerRegister, PolicyFragment())
                .commit()
        }

        binding.btnAddImage.setOnClickListener {

        }
    }
}