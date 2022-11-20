package com.example.dms_android.feature

import android.os.Bundle
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.dms_android.R
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.email.EnterEmailFragment
import com.example.dms_android.feature.register.ui.id.SetIdFragment
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment
import com.example.dms_android.feature.register.ui.school.ConfirmSchoolFragment
import com.example.dms_android.feature.register.ui.school.SchoolCertificationFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SchoolCertificationFragment())
            .commit()
    }

    fun changeFragment(index: Int) {
        when (index) {
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ConfirmSchoolFragment())
                    .commit()
            }

            2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, EnterEmailFragment())
                    .commit()
            }

            3 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, EmailCertificationFragment())
                    .commit()
            }

            4 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SetIdFragment())
                    .commit()
            }

            5 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SetPasswordFragment())
                    .commit()
            }
        }
    }
}