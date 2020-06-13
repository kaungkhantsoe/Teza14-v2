package com.kks.teza14.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by kaungkhantsoe on 06/03/2020.
 **/
public class CustomScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
