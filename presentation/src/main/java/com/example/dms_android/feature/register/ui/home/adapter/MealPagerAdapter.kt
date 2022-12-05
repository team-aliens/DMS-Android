package com.example.dms_android.feature.register.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dms_android.databinding.ItemMealBinding
import com.example.dms_android.feature.MainActivity
import com.example.feature_domain.entity.MealEntity

class MealPagerAdapter(mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}
