package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.IngredientItemBinding
import com.example.foodapp.data.entity.Ingredient

class IngredientAdapter :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private val listIngredient = mutableListOf<Ingredient>()

    fun setData(list: List<Ingredient>){
        listIngredient.clear()
        listIngredient.addAll(list)
        notifyDataSetChanged()
    }

    class IngredientViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.strIngredient
            binding.tvUnit.text = ingredient.strMeasure
            var isClick = false
            binding.tvIngredientName.setOnClickListener {
                when(isClick){
                    true -> {
                        isClick = false
                        binding.tvIngredientName.isChecked = false
                    }
                    false -> {
                        isClick = true
                        binding.tvIngredientName.isChecked = true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int = listIngredient.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(listIngredient[position])
    }
}