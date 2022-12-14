package com.example.dms_android.feature.register.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dms_android.base.BaseFragment
import com.example.dms_android.databinding.FragmentBreakfastBinding
import com.example.dms_android.R

class BreakFastFragment :BaseFragment<FragmentBreakfastBinding>(
    R.layout.fragment_breakfast
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {

    }
}