package com.example.dms_android.feature.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dms_android.R
import com.example.dms_android.feature.home.CafeteriaFragment

@SuppressLint("KotlinNullnessAnnotation")
class MealPagerAdapter(
    val context: Context
): RecyclerView.Adapter<MealPagerAdapter.MealPagerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPagerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false)
        return MealPagerViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(@NonNull holder: MealPagerViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.cardView.setBackgroundColor(R.drawable.non_meal_background)
        } else {
            holder.cardView.setBackgroundColor(R.drawable.meal_background)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    class MealPagerViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var ivImage: ImageView
        private var tvMeal: TextView
        var constraint: ConstraintLayout
        var cardView: CardView

        init {
            cardView = itemView.findViewById(R.id.materialCardView)
            ivImage = itemView.findViewById(R.id.ivMealType)
            tvMeal = itemView.findViewById(R.id.tvMeal)
            constraint = itemView.findViewById(R.id.constraint)
        }
    }
}
