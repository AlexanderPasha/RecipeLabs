package com.mannydev.recipelabs.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mannydev.recipelabs.R;
import com.mannydev.recipelabs.model.Result;

/**
 * Базовый клас для вывода рецептов, все остальные холдеры наследуются от него
 */

abstract class RecipeViewHolder extends RecyclerView.ViewHolder {

    ImageView ivImage;
    TextView txtTitle;
    TextView txtIngredients;
    RecipesAdapter.OnItemClickListener onItemClickListener;

    public RecipeViewHolder(View itemView, RecipesAdapter.OnItemClickListener listener) {
        super(itemView);

        ivImage = itemView.findViewById(R.id.ivImage);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtIngredients = itemView.findViewById(R.id.txtIngredients);

        this.onItemClickListener = listener;

    }

    protected abstract void bindRecipeView(Result result);

    protected void setOnItemClickListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAdapterPosition());
            }
        });
    }
}
