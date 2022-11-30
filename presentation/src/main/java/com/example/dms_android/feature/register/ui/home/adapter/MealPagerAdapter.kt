package com.example.dms_android.feature.register.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dms_android.databinding.ItemMealBinding
import com.example.feature_domain.entity.MealEntity

class MealPagerAdapter :
    ListAdapter<MealEntity.MealsValue, MealPagerAdapter.MainItemViewHolder>(MainListDiffCallback) {
    private lateinit var binding: ItemMealBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainItemViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealEntity.MealsValue) {
        }
    }

    object MainListDiffCallback : DiffUtil.ItemCallback<MealEntity.MealsValue>() {
        override fun areItemsTheSame(
            oldItem: MealEntity.MealsValue,
            newItem: MealEntity.MealsValue,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MealEntity.MealsValue,
            newItem: MealEntity.MealsValue,
        ): Boolean {
            return oldItem.date == newItem.date
        }

    }
}