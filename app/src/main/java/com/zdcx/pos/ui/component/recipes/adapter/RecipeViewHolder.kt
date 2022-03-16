package com.zdcx.pos.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zdcx.pos.R

import com.zdcx.pos.data.dto.recipes.RecipesItem

import com.zdcx.pos.databinding.RecipeItemBinding
import com.zdcx.pos.ui.base.listeners.RecyclerItemListener

/**
 * Created by AhmedEltaher
 */

class RecipeViewHolder(private val itemBinding: RecipeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: RecipesItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvCaption.text = recipesItem.description
        itemBinding.tvName.text = recipesItem.name
        Picasso.get().load(recipesItem.thumb).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(recipesItem) }
    }
}

