package com.zdcx.pos.ui.base.listeners

import com.zdcx.pos.data.dto.recipes.RecipesItem

/**
 * Created by AhmedEltaher
 */

interface RecyclerItemListener {
    fun onItemSelected(recipe : RecipesItem)
}
