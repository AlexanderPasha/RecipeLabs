package com.mannydev.recipelabs.view;

import android.view.View;

import com.mannydev.recipelabs.model.Result;

/**
 * Заглушка для вьюхи с мопсом
 */

public class PugViewHolder extends RecipeViewHolder {
    public PugViewHolder(View itemView, RecipesAdapter.OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void bindRecipeView(Result result) {

    }
}
