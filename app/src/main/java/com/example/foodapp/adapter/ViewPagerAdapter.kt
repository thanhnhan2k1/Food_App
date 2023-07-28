package com.example.foodapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val _listFragment = mutableListOf<Fragment>()
    override fun getItemCount(): Int = _listFragment.size

    override fun createFragment(position: Int): Fragment {
        return _listFragment[position]
    }
    fun addFragment(fragment: Fragment){
        _listFragment.add(fragment)
    }
}