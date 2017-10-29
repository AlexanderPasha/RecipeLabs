package com.mannydev.recipelabs.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Контроллер для получения интерфейсов для работы с разными API
 */

public class Controller {

    public static final String BASE_RECIPES_URL = "http://www.recipepuppy.com/";

    public static RecipesAPI getRecipesApi(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_RECIPES_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipesAPI recipesAPI = retrofit.create(RecipesAPI.class);
        return recipesAPI;
    }

}
