package com.mannydev.recipelabs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mannydev.recipelabs.controller.App;
import com.mannydev.recipelabs.controller.Controller;
import com.mannydev.recipelabs.controller.RecipesAPI;
import com.mannydev.recipelabs.model.Recipes;
import com.mannydev.recipelabs.model.Result;
import com.mannydev.recipelabs.model.Result_;
import com.mannydev.recipelabs.view.RecipesAdapter;
import com.mannydev.recipelabs.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Активити для поиска рецептов
 */

public class MainActivity extends AppCompatActivity implements RecipesAdapter.OnItemClickListener {

    public static final String TAG = "myLogs";
    public static final String TITLE = "title";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.toolbarSearch)
    Toolbar toolbarSearch;
    @BindView(R.id.rvRecipes)
    RecyclerView rvRecipes;
    @BindView(R.id.searchView)
    SearchView searchView;

    RecipesAPI recipesAPI;
    Recipes recipes;
    List<Result> resultListfromSearch;
    List<Result> resultListfromBox;
    RecipesAdapter recipesAdapter;
    Box<Result> resultBox;
    boolean ready;
    BoxStore boxStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_general);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        boxStore = App.getApp().getBoxStore();
        resultBox = boxStore.boxFor(Result.class);

        recipesAPI = Controller.getRecipesApi();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRecipes.setLayoutManager(mLayoutManager);

        recipesAdapter = new RecipesAdapter();
        recipesAdapter.setData(Collections.<Result>emptyList());
        recipesAdapter.setOnItemClickListener(this);

        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        rvRecipes.addItemDecoration(decoration);

        rvRecipes.setAdapter(recipesAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!s.equals("")){
                    showResultsFromTheBox(s);
                    searchView.clearFocus();
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length()>=2){
                    getRecipesFromSearch(s);
                    showResultsFromTheBox(s.substring(0,s.length()-1));
                }
                if(s.equals("")){
                    clearRvResults();
                }
                return false;
            }
        });

    }

    //Выводим результат поиска по БД на экран
    private void showResultsFromTheBox(String s){
        if(!s.equals("")){
            resultListfromBox = getRecipesFromBox(s);
            recipesAdapter.setData(resultListfromBox);
            recipesAdapter.notifyDataSetChanged();
        }else clearRvResults();
    }

    //Чистим наш список
    private void clearRvResults(){
        recipesAdapter.setData(Collections.<Result>emptyList());
        recipesAdapter.notifyDataSetChanged();
    }

    //Получаем рецепты с сайта и заносим в базу данных
    private void getRecipesFromSearch(String search){
        resultListfromSearch = new ArrayList<>();
        ready=false;
        recipesAPI.getRecipes(search).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                recipes = response.body();
                if (response.isSuccessful()) {
                    resultListfromSearch = recipes.getResults();
                    for (Result result : resultListfromSearch) {
                        if(resultBox.find(Result_.title,result.getTitle()).isEmpty()){
                            resultBox.put(result);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });

    }

    //Поиск рецептов в локальной БД
    private List<Result> getRecipesFromBox(String s){
        List<Result> queryList = resultBox.query().contains(Result_.title,s).build().find();
        queryList = addPug(queryList);
        return queryList;
    }

    @Override
    public void onItemClick(int position) {
        Result result = resultListfromBox.get(position);
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra(TITLE,result.getTitle());
        intent.putExtra("url",result.getHref());
        if(hasConnection(MainActivity.this)){
            startActivity(intent);
        }else
            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
        
    }

    //Добавляем мопса с список :))
    private List<Result> addPug(List<Result>list){
        Result resultPug = new Result();
        resultPug.setTitle("zPUG");
        list.add(resultPug);
        Collections.sort(list,titleComparator);
        return list;
    }

    //Мопса в конец списка
    public  Comparator<Result> titleComparator = new Comparator<Result>() {

        @Override
        public int compare(Result r1, Result r2) {
            if(!r1.getTitle().equals("zPUG")){
                return r1.getTitle().compareTo(r2.getTitle());
            }else return 1;

        }
    };

    //Проверка устройства на наличия связи с интернетом
    private static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }
}

