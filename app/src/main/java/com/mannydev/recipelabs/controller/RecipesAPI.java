package com.mannydev.recipelabs.controller;


import com.mannydev.recipelabs.model.Recipes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс для запросов на сайт http://www.recipepuppy.com
 */

public interface RecipesAPI {
    @GET("api/")
    Call<Recipes> getRecipes(@Query("q") String meal);

}