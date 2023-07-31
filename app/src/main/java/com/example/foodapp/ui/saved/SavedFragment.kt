package com.example.foodapp.ui.saved

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.model.Meal
import com.example.foodapp.data.room.MealRepository
import com.example.foodapp.databinding.FragmentSavedBinding
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout

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
        binding.rvListMeals.adapter = adapter

        viewModel.listFavoriteMeals.observe(viewLifecycleOwner) {
            handleSuccessMeal(binding.rvListMeals)
            it?.let { it1 -> adapter.setData(it1) }
            adapter.onItemClick = { mealItemBinding: MealItemBinding, meal: Meal ->
                mealItemBinding.tvMealName.setOnClickListener {
                    val action = SavedFragmentDirections.actionFragmentSavedToFragmentDetail(meal)
                    findNavController().navigate(action)
                }
                mealItemBinding.img.setOnClickListener {
                    val action = SavedFragmentDirections.actionFragmentSavedToFragmentDetail(meal)
                    findNavController().navigate(action)
                }
                mealItemBinding.btnLike.setImageResource(R.drawable.like_full_icon)
                mealItemBinding.btnLike.setOnClickListener {
                    viewModel.deleteMeal(meal)
                    adapter.removeMeal(meal)
                }
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
        }, 3000)

    }

}