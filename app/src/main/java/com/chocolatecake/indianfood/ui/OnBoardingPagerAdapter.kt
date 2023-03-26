package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.chocolatecake.indianfood.databinding.ItemOnboardingSliderBinding
import com.chocolatecake.indianfood.model.OnBoardingItem

class OnBoardingPagerAdapter(
    private val onBoardingDataList: List<OnBoardingItem>
) : PagerAdapter() {

    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val binding = ItemOnboardingSliderBinding.inflate(layoutInflater, container, false)

        val onBoardingData = onBoardingDataList[position]
        binding.imageSlider.setImageResource(onBoardingData.imageResource)
        binding.head.text = onBoardingData.titleText
        binding.desc.text = onBoardingData.descriptionText
        container.addView(binding.root)
        return binding.root
    }
}