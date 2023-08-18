package com.example.foodapp.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.ui.adapter.MealAdapter
import com.example.foodapp.databinding.FragmentSavedBinding
import com.example.foodapp.data.model.MealModel
import com.example.foodapp.data.FoodRepository
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.MealViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout

class SavedFragment : Fragment() {
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var binding: FragmentSavedBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var savedViewModel: SavedViewModel
    private lateinit var adapter: MealAdapter
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
        context?.let {
            val viewModelFactory = MealViewModelFactory(FoodRepository(it))
            mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
            savedViewModel = ViewModelProvider(this, viewModelFactory)[SavedViewModel::class.java]
        }
        adapter = MealAdapter()
        shimmerFrameLayout = binding.shimmerViewContainer
        binding.rvListMeals.adapter = adapter

        savedViewModel.getListFavoriteMeals()
        initObserver()

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

    private fun initObserver(){
        savedViewModel.listFavoriteMeals.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                adapter.setData(emptyList())
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setData(list)
                adapter.onItemClick = { type, meal ->
                    when (type) {
                        0 -> {
                            navigateToFragmentDetail(meal)
                        }
                        1 -> mealViewModel.insertMeal(meal)
                        2 -> mealViewModel.deleteMeal(meal)
                    }
                }
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
            }
        }
    }
}