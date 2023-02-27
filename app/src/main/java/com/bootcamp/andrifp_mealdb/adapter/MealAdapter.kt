package com.bootcamp.andrifp_mealdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.andrifp_mealdb.data.network.model.MealsItems
import com.bootcamp.andrifp_mealdb.databinding.MealRowLayoutBinding

class MealAdapter() : RecyclerView.Adapter<MealAdapter.ListViewAdapter>() {
    private var dataMeals: List<MealsItems> = listOf()
    private lateinit var onItemCallBack: IOnItemCallBack

    inner class ListViewAdapter(private val binding: MealRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealsItems) {
            binding.apply {
                data = item
                itemView.setOnClickListener {
                    onItemCallBack.onItemClickCallback(item)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewAdapter {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = MealRowLayoutBinding.inflate(layoutInflater, parent, false)
        return ListViewAdapter(rowBinding)
    }

    override fun getItemCount(): Int {
        return dataMeals.size
    }

    override fun onBindViewHolder(holder: ListViewAdapter, position: Int) {
        return holder.bind(dataMeals[position])
    }

    fun setData(data: List<MealsItems>) {
        dataMeals = data
        notifyDataSetChanged()
    }

    fun setOnClickCallback(action: IOnItemCallBack) {
        this.onItemCallBack = action
    }

    interface IOnItemCallBack {
        fun onItemClickCallback(data: MealsItems)
    }
}