package com.zdcx.pos.data.remote

import com.zdcx.pos.data.Resource
import com.zdcx.pos.data.dto.recipes.Recipes

/**
 * Created by AhmedEltaher
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<Recipes>
}
