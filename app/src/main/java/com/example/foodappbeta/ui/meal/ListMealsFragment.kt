package com.example.foodappbeta.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.foodappbeta.R
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.model.Constant
import com.example.foodappbeta.data.retrofit.RemoteFoodServiceImpl
import com.example.foodappbeta.data.room.FoodDatabase
import com.example.foodappbeta.databinding.FragmentListMealsBinding
import com.example.foodappbeta.ui.MealViewModelFactory
import com.example.foodappbeta.ui.adapter.MealAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ListMealsFragment : Fragment() {
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var binding: FragmentListMealsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListMealsBinding.inflate(inflater, container, false)
        shimmerFrameLayout = binding.shimmerViewContainer
        lottieAnimation = binding.noDataAnim
        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_make_it_now_button_move)
        binding.btnMakeIt.startAnimation(animation)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val now = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE - MMMM, dd yyyy", Locale.US).format(now)
        val constants = Constant
        val adapter = MealAdapter()

        context?.let {
            val remoteService = RemoteFoodServiceImpl.getRemoteFoodService()
            val db = FoodDatabase.getDatabase(it)
            val viewModelFactory =
                MealViewModelFactory(FoodRepository(remoteService, db.mealDAO(), db.categoryDAO()))
            val viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
//            viewModel.listCategories.observe(viewLifecycleOwner){
//                viewModel.getAllMeals()
//            }
            viewModel.list10Meals.observe(viewLifecycleOwner) { list ->
                when (list.isNullOrEmpty()) {
                    true -> {
                        adapter.setData(emptyList())
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
                        lottieAnimation.visibility = View.VISIBLE
                    }
                    false -> {
                        lottieAnimation.visibility = View.GONE
                        binding.btnSeeAll.visibility = View.VISIBLE
                        adapter.setData(list)
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
                    }
                }

                adapter.onItemClick = { type, meal ->
                    when (type) {
                        constants.ROOT_CLICK -> {
                            navigateToFragmentDetail(meal.idMeal)
                        }
                        constants.LIKE_CLICK -> viewModel.insertFavoriteMeal(meal)

                        constants.UNLIKE_CLICK -> viewModel.deleteFavoriteMeal(meal)
                    }
                }
            }

            viewModel.meal.observe(viewLifecycleOwner) { meal ->
                binding.tvMealName.text = meal.strMeal
                Picasso.get().load(meal.strMealThumb?.toUri()).into(binding.imgMeal)
            }
//            while (true){
            val animation = AnimationUtils.loadAnimation(it, R.anim.anim_make_it_now_button_move)
            binding.btnMakeIt.startAnimation(animation)
//            }

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
                lottieAnimation.visibility = View.GONE
                shimmerFrameLayout.startShimmerAnimation()
                shimmerFrameLayout.visibility = View.VISIBLE
                adapter.setData(emptyList())
                binding.btnSeeAll.visibility = View.INVISIBLE
                binding.swiperRefresh.isRefreshing = false
                viewModel.reloadListMeals()
            }
        }


        binding.tvDate.text = formatter
        binding.rvListRecentMeals.adapter = adapter


        binding.imvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_search)
        }
    }

    private fun navigateToFragmentDetail(idMeal: String) {
        val action = ListMealsFragmentDirections.actionFragmentHomeToFragmentDetail(idMeal)
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