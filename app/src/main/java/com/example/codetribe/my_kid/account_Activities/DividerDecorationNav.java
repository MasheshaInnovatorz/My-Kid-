package com.example.codetribe.my_kid.account_Activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerDecorationNav extends RecyclerView.ItemDecoration {
    public static final int[] ATTRS  = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    public DividerDecorationNav(Context context){
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();

    int childCount = parent.getChildCount();
    for(int i=1; i<childCount;i++){
        View child = parent.getChildAt(i);
        RecyclerView.LayoutParams params  =(RecyclerView.LayoutParams) child.getLayoutParams();
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + mDivider.getIntrinsicHeight();

        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(c);

    }
    }
}
