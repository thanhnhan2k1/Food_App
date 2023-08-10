package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.model.Constant
import com.example.foodapp.model.MealModel
import com.squareup.picasso.Picasso

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private val listMeals = mutableListOf<MealModel>()

    var onItemClick: ((Int, MealModel) -> Unit) = { _, _ ->
    }


    fun setData(list: List<MealModel>){
        listMeals.clear()
        listMeals.addAll(list)
        notifyDataSetChanged()
    }

    class MealViewHolder(private val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MealModel, onClick: (Int, MealModel) -> Unit){
            val constants = Constant

            binding.tvMealName.text = item.strMeal
            binding.tvTime.text = "45m"
            Picasso.get().load(item.strMealThumb?.toUri()).into(binding.img)

            binding.root.setOnClickListener { onClick(constants.ROOT_CLICK, item) }

            if(item.isLike){
                binding.btnLike.visibility = View.INVISIBLE
                binding.btnUnLike.visibility = View.VISIBLE
            }
            else{
                binding.btnLike.visibility = View.VISIBLE
                binding.btnUnLike.visibility = View.INVISIBLE
            }

            binding.btnLike.setOnClickListener {
                item.isLike = true
                onClick(constants.LIKE_CLICK, item)
                binding.btnLike.visibility = View.INVISIBLE
                binding.btnUnLike.visibility = View.VISIBLE
            }

            binding.btnUnLike.setOnClickListener {
                item.isLike = false
                onClick(constants.UNLIKE_CLICK, item)
                binding.btnLike.visibility = View.VISIBLE
                binding.btnUnLike.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int = listMeals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(listMeals[position], onItemClick)
    }
}