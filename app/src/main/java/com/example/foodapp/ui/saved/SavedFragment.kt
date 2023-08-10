package com.example.foodapp.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.ui.adapter.MealAdapter
import com.example.foodapp.databinding.FragmentSavedBinding
import com.example.foodapp.model.MealModel
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val viewModelFactory = MealViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        val adapter = MealAdapter()
        shimmerFrameLayout = binding.shimmerViewContainer
        binding.rvListMeals.adapter = adapter
        lifecycle.coroutineScope.launch {
            viewModel.getListFavoriteMeals().collect {
                if (it.isEmpty()) {
                    adapter.setData(emptyList())
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show()
                }
                else{
                    adapter.setData(it)
                    adapter.onItemClick = { type, meal ->
                        when (type) {
                            0 -> {
                                navigateToFragmentDetail(meal)
                            }
                            1 -> viewModel.insertMeal(meal)
                            2 -> viewModel.deleteMeal(meal)
                        }
                    }
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                }
            }
        }

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_saved_to_fragment_search)
        }
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    private fun navigateToFragmentDetail(meal: MealModel?) {
        val action = meal?.let { it1 ->
            SavedFragmentDirections.actionFragmentSavedToFragmentDetail(
                it1
            )
        }
        action?.let { it1 -> findNavController().navigate(it1) }
    }
}