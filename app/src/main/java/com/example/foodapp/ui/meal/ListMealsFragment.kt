package com.example.foodapp.ui.meal

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.data.retrofit.MealApi
import com.example.foodapp.data.retrofit.RetrofitBuilder
import com.example.foodapp.model.Meal
import com.example.foodapp.data.room.MealRepository
import com.example.foodapp.databinding.FragmentListMealsBinding
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.ui.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import java.time.DayOfWeek
import java.time.Month
import java.util.*

class ListMealsFragment : Fragment() {
    private var isLike = false
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentListMealsBinding.inflate(inflater, container, false)
        shimmerFrameLayout = binding.shimmerViewContainer

        val calender = Calendar.getInstance()
        //val time = LocalDate.now()
        var day = DayOfWeek.of(calender.get(Calendar.DAY_OF_WEEK)).name
        day = day.substring(0, 1) + day.substring(1).lowercase()
        var month = Month.of(calender.get(Calendar.MONTH)).name
        month = month.substring(0, 1) + month.substring(1).lowercase()
        val date = "$day - $month, " + calender.get(
            Calendar.DAY_OF_MONTH
        ) + " " + calender.get(Calendar.YEAR)
        binding.tvDate.text = date

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val repository = MealRepository(dataSource)
        val viewModelFactory = MealViewModelFactory(dataSource, repository, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        val adapter = MealRecycleView()

        binding.rvListRecentMeals.adapter = adapter

        viewModel.list10Meals.observe(viewLifecycleOwner) {
            handleSuccessMeal(binding.rvListRecentMeals)
            if (it != null) {
                adapter.setData(it)

                adapter.onItemClick = { mealItemBinding: MealItemBinding, meal: Meal ->
                    if (!meal.strMeal.isNullOrEmpty()) {
                        val newMeal = viewModel.getMealItem(meal.strMeal)
                        mealItemBinding.tvMealName.setOnClickListener {
                            navigateToFragmentDetail(newMeal)
                        }

                        mealItemBinding.img.setOnClickListener {
                            navigateToFragmentDetail(newMeal)
                        }
                        isLike = false
                        mealItemBinding.btnLike.setOnClickListener {
                            addOrRemoveMealToListFavorite(newMeal, mealItemBinding, viewModel)
                        }
                    }
                }
            }

        }

        viewModel.meal.observe(viewLifecycleOwner) {
            binding.tvMealName.text = it.strMeal
            Picasso.get().load(it.strMealThumb?.toUri()).into(binding.imgMeal)
        }

        binding.btnMakeIt.setOnClickListener {
            val meal = viewModel.getCurrentMeal()
            meal?.let {
                val action = ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(it)
                findNavController().navigate(action)
            }

        }

        binding.imvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
        }
//        val listMeals = MealRepository().get10RandomMeals()
        return binding.root
    }

    private fun navigateToFragmentDetail(meal: Meal?) {
        val action = meal?.let { it1 ->
            ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(
                it1
            )
        }
        action?.let { it1 -> findNavController().navigate(it1) }
    }

    private fun addOrRemoveMealToListFavorite(
        meal: Meal?,
        mealItemBinding: MealItemBinding,
        viewModel: MealViewModel
    ) {
        if (isLike) {
            isLike = false
            meal?.let { it1 ->
                viewModel.deleteMeal(it1)
            }
            mealItemBinding.btnLike.setImageResource(R.drawable.like_icon)
        } else {
            isLike = true
            meal?.let { it1 ->
                viewModel.insertMeal(it1)
            }
            mealItemBinding.btnLike.setImageResource(R.drawable.like_full_icon)
        }

    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout.stopShimmerAnimation()
    }

//    override fun onResume() {
//        super.onResume()
//        shimmerFrameLayout.startShimmerAnimation()
//    }

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