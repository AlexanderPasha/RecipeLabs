package com.mannydev.recipelabs.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mannydev.recipelabs.R;
import com.mannydev.recipelabs.model.Result;

import java.util.List;

/**
 * Адаптер для вывода рецептов в RecyclerView
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    public static final int RECIPE_VIEW = 1;
    public static final int PUG_VIEW = 0;

    private List<Result>list;
    private  OnItemClickListener onItemClickListener;

    public RecipesAdapter() {

    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType!=PUG_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_view, parent, false);
            return new RecipePuppyViewHolder(v,onItemClickListener);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_view_puppy, parent, false);
            return new PugViewHolder(v,onItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
            holder.bindRecipeView(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        Result result = list.get(position);
        if(result.getTitle().equals("zPUG")){
            return PUG_VIEW;
        }else return RECIPE_VIEW;
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public void setData(List<Result>list){
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
