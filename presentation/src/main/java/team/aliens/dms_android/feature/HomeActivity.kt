package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.dms_android.R
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityHomeBinding
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