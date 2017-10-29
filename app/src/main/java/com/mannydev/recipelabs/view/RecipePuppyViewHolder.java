package com.mannydev.recipelabs.view;

import android.view.View;

import com.mannydev.recipelabs.model.Result;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * ViewHolder для рецептов полученных с сайта http://www.recipepuppy.com
 */

public class RecipePuppyViewHolder extends RecipeViewHolder {

    //Заглушка для рецептов без картинки
    public static final String DEFAULT_IMG_URL ="https://pbs.twimg.com/profile_images/133465300/rpbig_400x400.png";

    public RecipePuppyViewHolder(View itemView, RecipesAdapter.OnItemClickListener listener) {
        super(itemView,listener);
        setOnItemClickListener();
    }

    @Override
    protected void bindRecipeView(Result result) {
        txtTitle.setText(result.getTitle().replace("&amp;","&").trim());
        if(!result.getThumbnail().equals("")){
            Picasso.with(itemView.getContext().getApplicationContext()).load(result.getThumbnail())
                    .transform(new CropCircleTransformation())
                    .fit().centerCrop().into(ivImage);
        }else
            Picasso.with(itemView.getContext().getApplicationContext()).load(DEFAULT_IMG_URL)
                    .transform(new CropCircleTransformation())
                    .fit().centerCrop().into(ivImage);
        txtIngredients.setText(result.getIngredients().trim());

    }
}
