package com.example.foodapp.ui.meal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.R
import com.example.foodapp.adapter.MealRecycleView
import com.example.foodapp.databinding.FragmentListMealsBinding
import com.example.foodapp.model.Constants
import com.example.foodapp.model.MealModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ListMealsFragment : Fragment() {
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var binding: FragmentListMealsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListMealsBinding.inflate(inflater, container, false)
        shimmerFrameLayout = binding.shimmerViewContainer
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val now = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE - MMMM, dd yyyy", Locale.US).format(now)
        binding.tvDate.text = formatter

        val application = requireNotNull(this.activity).application
        val dataSource = FoodDatabase.getDatabase(application).mealDAO()
        val viewModelFactory = MealViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        val adapter = MealRecycleView()

        binding.rvListRecentMeals.adapter = adapter
        viewModel.listCategories.observe(viewLifecycleOwner){
            viewModel.getAllMeals()
        }
        viewModel.list10Meals.observe(viewLifecycleOwner) {
            when (it.isNullOrEmpty()) {
                true -> {
                    Toast.makeText(context, "Get data fail!", Toast.LENGTH_SHORT).show()
                    adapter.setData(emptyList())
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE

                }
                false -> {
                    binding.btnSeeAll.visibility = View.VISIBLE
                    adapter.setData(it)
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                }
            }
            val constants = Constants
            adapter._onItemClick = { type, meal ->
                when (type) {
                    constants.rootClick -> {
                        navigateToFragmentDetail(meal)
                    }
                    constants.likeClick -> viewModel.insertMeal(meal)

                    constants.unlikeClick -> viewModel.deleteMeal(meal)
                }
            }
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

        binding.swiperRefresh.setOnRefreshListener {
            Log.d("List Meals Fragment", "onRefresh called from SwiperRefresh")
            shimmerFrameLayout.startShimmerAnimation()
            shimmerFrameLayout.visibility = View.VISIBLE
            adapter.setData(emptyList())
            binding.btnSeeAll.visibility = View.INVISIBLE
            binding.swiperRefresh.isRefreshing = false
            viewModel.reloadMealsFromAPI()
        }

        binding.imvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
        }
    }

    private fun navigateToFragmentDetail(meal: MealModel) {
        val action = ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(meal)
        findNavController().navigate(action)
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

}