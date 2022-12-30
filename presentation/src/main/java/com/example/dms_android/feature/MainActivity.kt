package com.example.dms_android.feature

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.dms_android.R
import com.example.dms_android.feature.navigator.RootDms
import com.example.dms_android.feature.register.ui.email.EmailCertificationFragment
import com.example.dms_android.feature.register.ui.email.EnterEmailFragment
import com.example.dms_android.feature.register.ui.id.SetIdFragment
import com.example.dms_android.feature.register.ui.password.SetPasswordFragment
import com.example.dms_android.feature.register.ui.school.ConfirmSchoolFragment
import com.example.dms_android.feature.register.ui.school.SchoolCertificationFragment
import com.example.dms_android.feature.splash.SplashViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val secondIntent = intent
            val route = secondIntent.getStringExtra("route")
            if (route != null) {
                RootDms(route)
            }
        }
    }

    override fun initView() {
    }
}