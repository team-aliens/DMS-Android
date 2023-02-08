package team.aliens.dms_android.feature

import android.os.Bundle
import com.example.dms_android.R
import com.example.dms_android.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseActivity
import team.aliens.dms_android.feature.register.ui.school.SchoolCertificationFragment

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerRegister, SchoolCertificationFragment()).commit()
    }
}