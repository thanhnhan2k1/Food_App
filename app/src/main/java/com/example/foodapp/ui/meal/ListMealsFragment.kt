package com.example.foodapp.ui.meal

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.data.room.MealRepository
import com.example.foodapp.databinding.FragmentListMealsBinding
import com.example.foodapp.model.MealModel
import com.example.foodapp.ui.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import java.time.DayOfWeek
import java.time.Month
import java.util.*

class ListMealsFragment : Fragment() {
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
        handleSuccessMeal(binding.rvListRecentMeals)

        viewModel.list10Meals.observe(viewLifecycleOwner) {
            when (it.isNullOrEmpty()) {
                true -> {
                    Toast.makeText(context, "Get data fail!", Toast.LENGTH_SHORT).show()
                }
                false -> {
//                    Log.d("ListMealFragment", "List: ${it.size}")
                    binding.btnSeeAll.visibility = View.VISIBLE
                    adapter.setData(it)
                    adapter._onItemClick = { type, meal ->
                        viewModel.getMealById(meal.idMeal)
                        viewModel.mealItem.observe(viewLifecycleOwner){
                            when (type) {
                                0 -> {
                                    navigateToFragmentDetail(it)
                                }
                                1 -> {
                                    viewModel.insertMeal(it)

                                }

                                2 -> {
                                    viewModel.deleteMeal(it)
                                }
                            }
                        }
                    }
//                    }
                }
            }
//            shimmerFrameLayout.stopShimmerAnimation()
//            shimmerFrameLayout.visibility = View.GONE
        }

        viewModel.meal.observe(viewLifecycleOwner) {
            binding.tvMealName.text = it.strMeal
            Picasso.get().load(it.strMealThumb?.toUri()).into(binding.imgMeal)
        }

        binding.btnMakeIt.setOnClickListener {
            when (val meal = viewModel.getCurrentMeal()) {
                null -> Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
                else -> {
                    val action =
                        ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(meal)
                    findNavController().navigate(action)
                }
            }
        }

        binding.imvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
        }
        return binding.root
    }

    private fun navigateToFragmentDetail(meal: MealModel?) {
        val action = meal?.let { it1 ->
            ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(
                it1
            )
        }
        action?.let { it1 -> findNavController().navigate(it1) }
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    private fun handleSuccessMeal(rv: RecyclerView) {
        rv.visibility = View.INVISIBLE
        shimmerFrameLayout.startShimmerAnimation()
        Handler().postDelayed({
            shimmerFrameLayout.stopShimmerAnimation()
            shimmerFrameLayout.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }, 2000)

    }
}