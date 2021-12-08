package com.me.myapplication.activity.adapter;



import androidx.cardview.widget.CardView;

public interface CardAdapterDetail {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
