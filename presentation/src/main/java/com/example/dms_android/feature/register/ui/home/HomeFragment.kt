package com.example.dms_android.feature.register.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentHomeBinding
import com.example.dms_android.R
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.register.ui.home.adapter.MealPagerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.vpMeal.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        binding.vpMeal.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지
        val pagerAdapter = MealPagerAdapter(requireActivity() as MainActivity)
        pagerAdapter.addFragment(BreakFastFragment())
        pagerAdapter.addFragment(LunchFragment())
        pagerAdapter.addFragment(DinnerFragment())

        binding.vpMeal.adapter = pagerAdapter
        binding.vpMeal.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun initView() {

    }
}

