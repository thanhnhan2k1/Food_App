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
import com.example.foodapp.data.FoodRepository
import com.example.foodapp.ui.adapter.MealAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.MealViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var mealViewModel: MealViewModel
    private val adapter by lazy {
        MealAdapter()
    }
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val viewModelFactory = MealViewModelFactory(FoodRepository(it))
            searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
            mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        }

        binding.rvListMeals.adapter = adapter
        initObserver()

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    when(newText.length){
                        1 -> searchViewModel.getListMealsByFirstLetter(newText)
                        else -> searchViewModel.getMealByName(newText)
                    }
                } else {
                    adapter.setData(emptyList())
                }
                return false
            }

        })
    }

    private fun initObserver() {
        searchViewModel.listFilterMeals.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                adapter.setData(it1)
                adapter.onItemClick = { type, meal ->
                    when (type) {
                        0 -> {
                            val action =
                                SearchFragmentDirections.actionFragmentSearchToFragmentDetail(
                                    meal
                                )
                            findNavController().navigate(action)
                        }
                        1 -> mealViewModel.insertMeal(meal)
                        2 -> mealViewModel.deleteMeal(meal)
                    }
                }
                if (it.isEmpty()) Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}