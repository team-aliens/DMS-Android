package com.example.dms_android.feature

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentContainerView
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.dms_android.R
import com.example.dms_android.feature.navigator.DmsApp
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.email.EnterEmailFragment
import com.example.dms_android.feature.register.ui.school.ConfirmSchoolFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DmsApp()
        }
    }

    override fun initView() {

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
        }
    }
}