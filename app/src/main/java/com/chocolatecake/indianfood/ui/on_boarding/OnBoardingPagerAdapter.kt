package com.chocolatecake.indianfood.ui.on_boarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.chocolatecake.indianfood.databinding.ItemOnboardingBinding
import com.chocolatecake.indianfood.model.OnBoardingItem

class OnBoardingPagerAdapter(
    private val onBoardingDataList: List<OnBoardingItem>
) : PagerAdapter() {

    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val binding = ItemOnboardingBinding.inflate(layoutInflater, container, false)
        val onBoardingData = onBoardingDataList[position]

        binding.apply {
            imageViewOnboarding.setImageResource(onBoardingData.imageResource)
            textViewHeadline.text = onBoardingData.titleText
            textViewDescription.text = onBoardingData.descriptionText
        }
        container.addView(binding.root)
        return binding.root
    }
}