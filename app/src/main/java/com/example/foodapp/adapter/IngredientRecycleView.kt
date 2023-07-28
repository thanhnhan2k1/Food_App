package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.IngredientItemBinding
import com.example.foodapp.model.Ingredient
import com.example.foodapp.model.Meal

class IngredientRecycleView(private val meal: Meal) :
    RecyclerView.Adapter<IngredientRecycleView.IngeredientViewHolder>() {
    private val listIngredient = mutableListOf<Ingredient>()


    fun toListIngredient() {
        listIngredient.clear()
        if(!meal.strIngredient1.isNullOrBlank()){
            val ingredient1 =
                Ingredient(strIngredient = meal.strIngredient1, strMeasure = meal.strMeasure1 ?: "--" )
            listIngredient.add(ingredient1)
        }
        if(!meal.strIngredient2.isNullOrBlank()){
            val ingredient2 =
                Ingredient(strIngredient = meal.strIngredient2, strMeasure = meal.strMeasure2 ?: "--")
            listIngredient.add(ingredient2)
        }
        if(!meal.strIngredient3.isNullOrBlank()){
            val ingredient3 =
                Ingredient(strIngredient = meal.strIngredient3, strMeasure = meal.strMeasure3 ?: "--")
            listIngredient.add(ingredient3)
        }
        if(!meal.strIngredient4.isNullOrBlank()){
            val ingredient4 =
                Ingredient(strIngredient = meal.strIngredient4, strMeasure = meal.strMeasure4 ?: "--")
            listIngredient.add(ingredient4)
        }
        if(!meal.strIngredient5.isNullOrBlank()){
            val ingredient5 =
                Ingredient(strIngredient = meal.strIngredient5, strMeasure = meal.strMeasure5 ?: "--")
            listIngredient.add(ingredient5)
        }
        if(!meal.strIngredient6.isNullOrBlank()){
            val ingredient6 =
                Ingredient(strIngredient = meal.strIngredient6, strMeasure = meal.strMeasure6 ?: "--")
            listIngredient.add(ingredient6)
        }
        if(!meal.strIngredient7.isNullOrBlank()){
            val ingredient7 =
                Ingredient(strIngredient = meal.strIngredient7, strMeasure = meal.strMeasure7 ?: "--")
            listIngredient.add(ingredient7)
        }
        if(!meal.strIngredient8.isNullOrBlank()){
            val ingredient8 =
                Ingredient(strIngredient = meal.strIngredient8, strMeasure = meal.strMeasure8 ?: "--")
            listIngredient.add(ingredient8)
        }
        if(!meal.strIngredient9.isNullOrBlank()){
            val ingredient9 =
                Ingredient(strIngredient = meal.strIngredient9, strMeasure = meal.strMeasure9 ?: "--")
            listIngredient.add(ingredient9)
        }
        if(!meal.strIngredient10.isNullOrBlank()){
            val ingredient10 =
                Ingredient(strIngredient = meal.strIngredient10, strMeasure = meal.strMeasure10 ?: "--")
            listIngredient.add(ingredient10)
        }
        if(!meal.strIngredient11.isNullOrBlank()){
            val ingredient11 =
                Ingredient(strIngredient = meal.strIngredient11, strMeasure = meal.strMeasure11 ?: "--")
            listIngredient.add(ingredient11)
        }
        if(!meal.strIngredient12.isNullOrBlank()){
            val ingredient12 =
                Ingredient(strIngredient = meal.strIngredient12, strMeasure = meal.strMeasure12 ?: "--")
            listIngredient.add(ingredient12)
        }
        if(!meal.strIngredient13.isNullOrBlank()){
            val ingredient13 =
                Ingredient(strIngredient = meal.strIngredient13, strMeasure = meal.strMeasure13 ?: "--")
            listIngredient.add(ingredient13)
        }
        if(!meal.strIngredient14.isNullOrBlank()){
            val ingredient14 =
                Ingredient(strIngredient = meal.strIngredient14, strMeasure = meal.strMeasure14 ?: "--")
            listIngredient.add(ingredient14)
        }
        if(!meal.strIngredient15.isNullOrBlank()){
            val ingredient15 =
                Ingredient(strIngredient = meal.strIngredient15, strMeasure = meal.strMeasure15 ?: "--")
            listIngredient.add(ingredient15)
        }
        if(!meal.strIngredient16.isNullOrBlank()){
            val ingredient16 =
                Ingredient(strIngredient = meal.strIngredient16, strMeasure = meal.strMeasure16 ?: "--")
            listIngredient.add(ingredient16)
        }
        if(!meal.strIngredient17.isNullOrBlank()){
            val ingredient17 =
                Ingredient(strIngredient = meal.strIngredient17, strMeasure = meal.strMeasure17 ?: "--")
            listIngredient.add(ingredient17)
        }
        if(!meal.strIngredient18.isNullOrBlank()){
            val ingredient18 =
                Ingredient(strIngredient = meal.strIngredient18, strMeasure = meal.strMeasure18 ?: "--")
            listIngredient.add(ingredient18)
        }
        if(!meal.strIngredient19.isNullOrBlank()){
            val ingredient19 =
                Ingredient(strIngredient = meal.strIngredient19, strMeasure = meal.strMeasure19 ?: "--")
            listIngredient.add(ingredient19)
        }
        if(!meal.strIngredient20.isNullOrBlank()){
            val ingredient20 =
                Ingredient(strIngredient = meal.strIngredient20, strMeasure = meal.strMeasure20 ?: "--")
            listIngredient.add(ingredient20)
        }
        notifyDataSetChanged()
    }

    class IngeredientViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.strIngredient
            binding.tvUnit.text = ingredient.strMeasure
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngeredientViewHolder {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngeredientViewHolder(binding)
    }

    override fun getItemCount(): Int = listIngredient.size

    override fun onBindViewHolder(holder: IngeredientViewHolder, position: Int) {
        holder.bind(listIngredient[position])
    }
}