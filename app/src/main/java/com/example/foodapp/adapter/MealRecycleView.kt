package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.model.Meal
import com.squareup.picasso.Picasso

class MealRecycleView : RecyclerView.Adapter<MealRecycleView.MealViewHolder>() {
    private val listMeals = mutableListOf<Meal>()

    var onItemClick: ((MealItemBinding, Meal) -> Unit) ?= null

    fun setData(list: List<Meal>){
        listMeals.clear()
        listMeals.addAll(list)
        notifyDataSetChanged()
    }
    fun removeMeal(meal: Meal){
        listMeals.remove(meal)
        notifyDataSetChanged()
    }
    fun addMeal(meal: Meal){
        listMeals.add(meal)
        notifyDataSetChanged()
    }

    class MealViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meal){
            binding.tvMealName.text = item.strMeal
            binding.tvTime.text = "45m"
            Picasso.get().load(item.strMealThumb?.toUri()).into(binding.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int = listMeals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(listMeals[position])
        onItemClick?.invoke(holder.binding, listMeals[position])
    }
}