package com.example.dms_android.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.example.dms_android.R
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityHomeBinding
import com.example.dms_android.feature.navigator.DmsApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(
    R.layout.activity_home
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }

    override fun initView() {

    }
}