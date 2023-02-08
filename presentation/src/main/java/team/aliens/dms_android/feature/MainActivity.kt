package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.dms_android.R
import com.example.dms_android.feature.navigator.RootDms

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