package com.example.dms_android.feature

import android.os.Bundle
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityRegisterBinding
import com.example.dms_android.R
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.email.EnterEmailFragment
import com.example.dms_android.feature.register.ui.id.SetIdFragment
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment
import com.example.dms_android.feature.register.ui.school.ConfirmSchoolFragment
import com.example.dms_android.feature.register.ui.school.SchoolCertificationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(
    R.layout.activity_register
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerRegister, SchoolCertificationFragment())
            .commit()
    }
}