package com.example.foodapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodapp.R
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.adapter.IngredientRecycleView
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.ui.meal.MealViewModel
import com.example.foodapp.ui.meal.MealViewModelFactory
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        val meal = args.meal
        Picasso.get().load(meal.strMealThumb?.toUri()).into(binding.imgMeal)
        binding.tvMealName.text = meal.strMeal
        binding.tvMake.text = meal.strInstructions

        val adapter = IngredientRecycleView(meal)
        adapter.toListIngredient()
        binding.rvListIngredients.adapter = adapter

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val viewModelFactory = MealViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        if(meal.isLike){
            binding.tvAddToMyList.visibility = View.INVISIBLE
            binding.tvRemoveToMyList.visibility = View.VISIBLE
        }

        binding.tvAddToMyList.setOnClickListener {
            viewModel.insertMeal(meal)
            binding.tvAddToMyList.visibility = View.INVISIBLE
            binding.tvRemoveToMyList.visibility = View.VISIBLE
        }
        binding.tvRemoveToMyList.setOnClickListener {
            viewModel.deleteMeal(meal)
            binding.tvAddToMyList.visibility = View.VISIBLE
            binding.tvRemoveToMyList.visibility = View.INVISIBLE
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_detail_to_fragment_search)
        }
        return binding.root
    }

}