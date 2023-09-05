package com.example.foodappbeta.ui.meal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodappbeta.R
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.model.Constant
import com.example.foodappbeta.data.retrofit.RemoteFoodClient
import com.example.foodappbeta.data.room.FoodDatabase
import com.example.foodappbeta.databinding.FragmentListMealsBinding
import com.example.foodappbeta.ui.MealViewModelFactory
import com.example.foodappbeta.ui.adapter.MealAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ListMealsFragment : Fragment() {
    private lateinit var binding: FragmentListMealsBinding
    private lateinit var viewModel: MealViewModel
    private val adapter = MealAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListMealsBinding.inflate(inflater, container, false)
        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_make_it_now_button_move)
        binding.btnMakeIt.startAnimation(animation)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val remoteService = RemoteFoodClient.getRemoteFoodService()
        val db = FoodDatabase.getDatabase(requireContext())
        val viewModelFactory =
            MealViewModelFactory(FoodRepository(remoteService, db.mealDAO(), db.categoryDAO()))
        viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        initView()
        initAction()
        observeData()

    }

    private fun navigateToFragmentDetail(idMeal: String) {
        val action = ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(idMeal)
        findNavController().navigate(action)
    }

    private fun initView() {
        val now = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE - MMMM, dd yyyy", Locale.US).format(now)
        val animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anim_make_it_now_button_move)

        binding.tvDate.text = formatter
        binding.rvListRecentMeals.adapter = adapter

        binding.btnMakeIt.startAnimation(animation)

    }

    private fun initAction() {
        val constants = Constant

        adapter.onItemClick = { type, meal ->
            when (type) {
                constants.ROOT_CLICK -> {
                    navigateToFragmentDetail(meal.idMeal)
                }
                constants.LIKE_CLICK -> viewModel.insertFavoriteMeal(meal)

                constants.UNLIKE_CLICK -> viewModel.deleteFavoriteMeal(meal)
            }
        }

        binding.btnMakeIt.setOnClickListener {
            when (val meal = viewModel.getCurrentMeal()) {
                null -> Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
                else -> {
                    val action =
                        ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(meal.idMeal)
                    findNavController().navigate(action)
                }
            }
        }

        binding.swiperRefresh.setOnRefreshListener {
            binding.shimmerViewContainer.startShimmerAnimation()
            binding.shimmerViewContainer.visibility = View.VISIBLE
            adapter.setData(emptyList())
            binding.btnSeeAll.visibility = View.INVISIBLE
            binding.swiperRefresh.isRefreshing = false
            binding.animLoadImg.visibility = View.VISIBLE

            viewModel.reloadListMeals()
            viewModel.reloadMealRandom()
        }

        binding.imvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
        }
    }

    private fun observeData() {
        viewModel.list10Meals.observe(viewLifecycleOwner) { list ->
            when (list.isNullOrEmpty()) {
                true -> {
                    adapter.setData(emptyList())
                    binding.shimmerViewContainer.stopShimmerAnimation()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.animLoadImg.visibility = View.VISIBLE
                    binding.noDataAnim.visibility = View.VISIBLE
                }
                false -> {
                    binding.animLoadImg.visibility = View.GONE
                    binding.btnSeeAll.visibility = View.VISIBLE
                    binding.noDataAnim.visibility = View.GONE
                    adapter.setData(list)
                    Log.d("List Meal", "${list.size}")
                    binding.shimmerViewContainer.stopShimmerAnimation()
                    binding.shimmerViewContainer.visibility = View.GONE
                }
            }

        }

        viewModel.mealRandom.observe(viewLifecycleOwner) { meal ->
            binding.animLoadImg.visibility = View.INVISIBLE
            binding.tvMealName.text = meal.strMeal
            Picasso.get().load(meal.strMealThumb?.toUri()).into(binding.imgMeal)
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

}