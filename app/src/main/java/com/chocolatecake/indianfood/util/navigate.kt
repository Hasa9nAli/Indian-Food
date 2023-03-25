package com.chocolatecake.indianfood.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chocolatecake.indianfood.R


enum class NavigationState {
    ADD,
    REPLACE,
    REMOVE,
}


fun FragmentActivity.navigateTo(to: Fragment) {
    changeNavigation(this, NavigationState.ADD, to)
}


fun FragmentActivity.navigateAndReplaceTo(to: Fragment) {
    changeNavigation(this, NavigationState.REPLACE, to)
}

fun FragmentActivity.back(to: Fragment) {
    changeNavigation(this, NavigationState.REMOVE, to)
}

private fun changeNavigation(activity: FragmentActivity, state: NavigationState, to: Fragment) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    when (state) {
        NavigationState.ADD -> transaction.add(R.id.main_fragment_container, to)
        NavigationState.REMOVE -> transaction.remove(to)
        NavigationState.REPLACE -> transaction.replace(R.id.main_fragment_container, to)
    }
    transaction.addToBackStack(null).commit()
}


//// this used in fragment when click on item
//override fun onClickItem(aragment: TypeA) {
//    requireActivity().navigateTo(NameFragment.newInstance(arg))
//}


//this used when you have icon back
//private fun arrowBack() {
//    binding.arrowIcon.setOnClickListener {
//        this.parentFragmentManager.popBackStack()
//    }
//}


//
//this used in fragment page to receive data
//companion object {
//    fun newInstance(argument: TypeOfArgument) = NameFragment().apply {
//        arguments = Bundle().apply {
//            putSerializable(Constants.KEY_ARGUMENT, argument)
//        }
//    }
//}
