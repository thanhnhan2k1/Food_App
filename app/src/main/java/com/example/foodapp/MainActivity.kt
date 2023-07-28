package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.R.id.*
import com.example.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val _binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        _binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{ _, navDestination: NavDestination, _ ->
            if(navDestination.id == fragment_detail || navDestination.id == fragment_search){
                _binding.bottomNav.visibility = View.GONE
            }
            else {
                _binding.bottomNav.visibility = View.VISIBLE
            }
        }
        // Set up the action bar for use with the NavController
//        setupActionBarWithNavController(navController)
//        val viewPagerAdapter = ViewPagerAdapter(this.supportFragmentManager, lifecycle)
//        val listMealsFragment = ListMealsFragment()
//        val savedFragment = SavedFragment()
//        val profileFragment = ProfileFragment()
//
//        viewPagerAdapter.addFragment(listMealsFragment)
//        viewPagerAdapter.addFragment(savedFragment)
//        viewPagerAdapter.addFragment(profileFragment)
//
//        _binding.viewpager.adapter = viewPagerAdapter
//        _binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                when(position){
//                    0 -> _binding.bottomNav.menu.findItem(R.id.mHome).isChecked = true
//                    1 -> _binding.bottomNav.menu.findItem(R.id.mSaved).isChecked = true
//                    2 -> _binding.bottomNav.menu.findItem(R.id.mProfile).isChecked = true
//                }
//                super.onPageSelected(position)
//            }
//        })

//        _binding.bottomNav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.mHome -> {
//                    findNavController().navigate()
//                    return@setOnItemSelectedListener true
//                }
//                R.id.mSaved -> {
//                    _binding.viewpager.currentItem = 1
//                    return@setOnItemSelectedListener true
//                }
//                R.id.mProfile -> {
//                    _binding.viewpager.currentItem = 2
//                    return@setOnItemSelectedListener true
//                }
//            }
//            false
//        }
//
//    }
    }
}