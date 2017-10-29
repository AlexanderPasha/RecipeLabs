package com.mannydev.recipelabs.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Класс добавляет промежутки между вьюхами
 */


public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View v, RecyclerView parent, RecyclerView.State st) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if(parent.getChildAdapterPosition(v) == 0) {
            outRect.top = space;
        }
    }
}
