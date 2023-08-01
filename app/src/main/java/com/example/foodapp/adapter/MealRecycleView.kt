package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.model.Meal
import com.squareup.picasso.Picasso

class MealRecycleView : RecyclerView.Adapter<MealRecycleView.MealViewHolder>() {
    private val listMeals = mutableListOf<Meal>()

//    var onItemClick: ((MealItemBinding, Meal) -> Unit) ?= null
    var _onItemClick: ((Int, Meal) -> Unit) = { _, _ ->
    }
    var _setView: ((Int) -> Unit) = {
    }

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

    class MealViewHolder(private val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meal, onClick: (Int, Meal) -> Unit, setView: (Int) -> Unit){
            binding.tvMealName.text = item.strMeal
            binding.tvTime.text = "45m"
            Picasso.get().load(item.strMealThumb?.toUri()).into(binding.img)
            setView(0).let{
                binding.btnLike.visibility = View.INVISIBLE
                binding.btnUnLike.visibility = View.VISIBLE
            }
            binding.root.setOnClickListener { onClick(0, item) }
            binding.btnLike.setOnClickListener {
                onClick(1, item)
                binding.btnLike.visibility = View.INVISIBLE
                binding.btnUnLike.visibility = View.VISIBLE
            }
            binding.btnUnLike.setOnClickListener {
                onClick(2, item)
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
        holder.bind(listMeals[position], _onItemClick, _setView)
//        onItemClick?.invoke(holder.binding, listMeals[position])

    }
}