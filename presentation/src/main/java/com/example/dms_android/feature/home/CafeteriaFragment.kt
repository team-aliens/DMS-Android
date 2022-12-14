package com.example.dms_android.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.dms_android.R
import com.example.dms_android.base.BaseActivity
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentHomeBinding
import com.example.dms_android.feature.home.adapter.MealPagerAdapter

class CafeteriaFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createCardHolder()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun createCardHolder() {

        binding.vpMeal.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpMeal.adapter = activity?.let { MealPagerAdapter(it.applicationContext) }
        binding.vpMeal.offscreenPageLimit = 1

        val nextItemVisibleWidth = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemMargin =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslation = nextItemVisibleWidth + currentItemMargin
        binding.vpMeal.setPageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslation * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
        }
        val itemDecoration = PagerMarginItemDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.vpMeal.addItemDecoration(itemDecoration)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }
}

