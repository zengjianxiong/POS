package com.zdcx.pos.data.remote

import com.zdcx.pos.data.Resource
import com.zdcx.pos.data.dto.recipes.Recipes
import com.zdcx.pos.data.dto.recipes.RecipesItem
import com.zdcx.pos.data.error.NETWORK_ERROR
import com.zdcx.pos.data.error.NO_INTERNET_CONNECTION
import com.zdcx.pos.data.remote.service.RecipesService
import com.zdcx.pos.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException



/**
 * Created by AhmedEltaher
 */

class RemoteData(private val serviceGenerator: ServiceGenerator,private val networkConnectivity: NetworkConnectivity) :
    RemoteDataSource {
    override suspend fun requestRecipes(): Resource<Recipes> {
        val recipesService = serviceGenerator.createService(RecipesService::class.java)
        return when (val response = processCall(recipesService::fetchRecipes)) {
            is List<*> -> {
                Resource.Success(data = Recipes(response as ArrayList<RecipesItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
