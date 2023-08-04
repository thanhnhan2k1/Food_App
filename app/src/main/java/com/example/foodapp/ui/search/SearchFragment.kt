package com.example.foodapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var viewModel: MealViewModel
    private val adapter by lazy {
        MealRecycleView()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val viewModelFactory = MealViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

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
                        1 -> viewModel.getListMealsByFirstLetter(newText)
                        else -> viewModel.getMealByName(newText)
                    }
                } else {
                    adapter.setData(emptyList())
                }
                return false
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initObserver() {
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
                if (it.isEmpty()) Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}