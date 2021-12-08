package com.me.myapplication.activity.adapter;


public class CardItemDetail {

    private String mTextResource;
    private int mTitleResource;

    public CardItemDetail(int title, String text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public String getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
