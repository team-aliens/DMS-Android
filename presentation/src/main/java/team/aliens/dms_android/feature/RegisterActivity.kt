package team.aliens.dms_android.feature

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.base.BaseActivity
import team.aliens.dms_android.feature.register.ui.school.SchoolCertificationFragment
import team.aliens.presentation.R
import team.aliens.presentation.databinding.ActivityRegisterBinding

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