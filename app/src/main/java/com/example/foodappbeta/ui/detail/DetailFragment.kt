package com.example.foodappbeta.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodappbeta.R
import com.example.foodappbeta.ui.adapter.IngredientAdapter
import com.example.foodappbeta.databinding.FragmentDetailBinding
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.retrofit.RemoteFoodServiceImpl
import com.example.foodappbeta.data.room.FoodDatabase
import com.example.foodappbeta.ui.meal.MealViewModel
import com.example.foodappbeta.ui.MealViewModelFactory
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idMeal = args.idMeal
        val adapter = IngredientAdapter()

        context?.let {
            val remoteService = RemoteFoodServiceImpl.getRemoteFoodService()
            val db = FoodDatabase.getDatabase(it)
            val mealViewModelFactory = MealViewModelFactory(FoodRepository(remoteService, db.mealDAO(), db.categoryDAO()))
            val mealViewModel = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]

            val ingredientViewModelFactory = IngredientViewModelFactory()
            val ingredientViewModel = ViewModelProvider(this, ingredientViewModelFactory)[IngredientViewModel::class.java]

            mealViewModel.getMealByID(idMeal)
            mealViewModel.mealDetail.observe(viewLifecycleOwner){ meal ->
                val animation = AnimationUtils.loadAnimation(context, R.anim.slide)

                binding.loadingScene.animLoading.visibility = View.INVISIBLE
                binding.detailScene.visibility = View.VISIBLE
                binding.detailScene.startAnimation(animation)
                binding.rvListIngredients.adapter = adapter

                binding.tvAddToMyList.setOnClickListener {
                    mealViewModel.insertFavoriteMeal(meal)
                    binding.tvAddToMyList.visibility = View.INVISIBLE
                    binding.tvRemoveToMyList.visibility = View.VISIBLE
                }

                binding.tvRemoveToMyList.setOnClickListener {
                    mealViewModel.deleteFavoriteMeal(meal)
                    binding.tvAddToMyList.visibility = View.VISIBLE
                    binding.tvRemoveToMyList.visibility = View.INVISIBLE
                }
                ingredientViewModel.setMeal(meal)

                if(meal.isLike){
                    binding.tvAddToMyList.visibility = View.INVISIBLE
                    binding.tvRemoveToMyList.visibility = View.VISIBLE
                }
                else{
                    binding.tvAddToMyList.visibility = View.VISIBLE
                    binding.tvRemoveToMyList.visibility = View.INVISIBLE
                }
            }


            ingredientViewModel.meal.observe(viewLifecycleOwner){
                ingredientViewModel.setListIngredient()
                Picasso.get().load(it.strMealThumb?.toUri()).into(binding.imgMeal)
                binding.tvMealName.text = it.strMeal
                binding.tvMake.text = it.strInstructions
            }


            ingredientViewModel.listIngredient.observe(viewLifecycleOwner){ list ->
                adapter.setData(list)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_detail_to_fragment_search)
        }
    }

}