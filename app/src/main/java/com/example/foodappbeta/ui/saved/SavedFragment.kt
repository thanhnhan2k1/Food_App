package com.example.foodappbeta.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodappbeta.R
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.retrofit.RemoteFoodClient
import com.example.foodappbeta.data.room.FoodDatabase
import com.example.foodappbeta.databinding.FragmentSavedBinding
import com.example.foodappbeta.ui.MealViewModelFactory
import com.example.foodappbeta.ui.adapter.MealAdapter
import com.example.foodappbeta.ui.meal.MealViewModel

class SavedFragment : Fragment() {
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

        val remoteService = RemoteFoodClient.getRemoteFoodService()
        val db = FoodDatabase.getDatabase(requireContext())
        val viewModelFactory =
            MealViewModelFactory(FoodRepository(remoteService, db.mealDAO(), db.categoryDAO()))
        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        savedViewModel = ViewModelProvider(this, viewModelFactory)[SavedViewModel::class.java]

        adapter = MealAdapter()
        binding.rvListMeals.adapter = adapter

        savedViewModel.getListFavoriteMeals()
        initObserver()

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_saved_to_fragment_search)
        }
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    private fun navigateToFragmentDetail(idMeal: String) {
        val action = SavedFragmentDirections.actionFragmentSavedToFragmentDetail(idMeal)
        findNavController().navigate(action)
    }

    private fun initObserver() {
        savedViewModel.listFavoriteMeals.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                adapter.setData(emptyList())
                binding.shimmerViewContainer.stopShimmerAnimation()
                binding.shimmerViewContainer.visibility = View.GONE
                binding.noDataAnim.visibility = View.VISIBLE
            } else {
                binding.noDataAnim.visibility = View.INVISIBLE
                adapter.setData(list)
                adapter.onItemClick = { type, meal ->
                    when (type) {
                        0 -> {
                            navigateToFragmentDetail(meal.idMeal)
                        }
                        1 -> savedViewModel.insertFavoriteMeal(meal)
                        2 -> {
                            savedViewModel.deleteFavoriteMeal(meal)
                        }
                    }
                }
                binding.shimmerViewContainer.stopShimmerAnimation()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        }
    }
}

