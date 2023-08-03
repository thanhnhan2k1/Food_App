package com.example.foodapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val viewModelFactory = MealViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        val adapter = MealRecycleView()
        binding.rvListMeals.adapter = adapter

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank() && newText.length == 1) {
                    viewModel.getListMealsByFirstLetter(newText)
                    viewModel.listFilterMeals.observe(viewLifecycleOwner) {
                        it?.let { it1 ->
                            adapter.setData(it1)
                            adapter._onItemClick = { type, meal ->
                                when (type) {
                                    0 -> {
                                        val action =
                                            SearchFragmentDirections.actionFragmentSearchToFragmentDetail(
                                                meal
                                            )
                                        findNavController().navigate(action)
                                    }
                                    1 -> viewModel.insertMeal(meal)
                                    2 -> viewModel.deleteMeal(meal)
                                }
                            }
                            if(it.isEmpty()) Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    adapter.setData(emptyList())
                }
                return false
            }

        })
        return binding.root
    }

}