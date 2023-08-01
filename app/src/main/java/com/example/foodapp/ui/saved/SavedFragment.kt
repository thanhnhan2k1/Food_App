package com.example.foodapp.ui.saved

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.model.Meal
import com.example.foodapp.data.room.MealRepository
import com.example.foodapp.databinding.FragmentSavedBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSavedBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val repository = MealRepository(dataSource)
        val viewModelFactory = MealViewModelFactory(dataSource, repository, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        val adapter = MealRecycleView()
        shimmerFrameLayout = binding.shimmerViewContainer
        shimmerFrameLayout.startShimmerAnimation()
        binding.rvListMeals.adapter = adapter
        handleSuccessMeal(binding.rvListMeals)
        lifecycle.coroutineScope.launch {
            viewModel.getListFavoriteMeals().collect {
                if (it.isEmpty()) {
                    Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show()
                }

                adapter.setData(it)
                adapter._onItemClick = { type, meal ->
                    when (type) {
                        0 -> navigateToFragmentDetail(meal)
                        1 -> viewModel.insertMeal(meal)
                        2 -> viewModel.deleteMeal(meal)
                    }
                }
//                shimmerFrameLayout.stopShimmerAnimation()
//                shimmerFrameLayout.visibility = View.GONE
//                adapter._setView = { type ->
//                    when (type) {
//                        0 -> Log.d("SavedFragment", "Set list favorite view")
//                    }
//                }
            }
        }

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_saved_to_fragment_search)
        }

        return binding.root
    }

    private fun handleSuccessMeal(rv: RecyclerView) {
        rv.visibility = View.INVISIBLE
        shimmerFrameLayout.startShimmerAnimation()
        Handler().postDelayed({
            shimmerFrameLayout.stopShimmerAnimation()
            shimmerFrameLayout.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }, 2000)

    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout.stopShimmerAnimation()
    }

    override fun onResume() {
        shimmerFrameLayout.startShimmerAnimation()
        super.onResume()
    }

    private fun navigateToFragmentDetail(meal: Meal?) {
        val action = meal?.let { it1 ->
            SavedFragmentDirections.actionFragmentSavedToFragmentDetail(
                it1
            )
        }
        action?.let { it1 -> findNavController().navigate(it1) }
    }
}