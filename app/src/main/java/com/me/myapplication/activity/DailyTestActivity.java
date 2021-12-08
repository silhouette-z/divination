package com.me.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.me.myapplication.activity.adapter.CardItemDetail;
import com.me.myapplication.activity.adapter.CardPagerAdapterDetail;
import com.me.myapplication.activity.model.ShadowTransformer;
import com.me.myapplication.R;

//import me.zqn.astrologyapp.bean.DailyAstro;
//import me.zqn.astrologyapp.util.OkHttpUtils;
//import me.zqn.astrologyapp.util.UrlUtil;

//public class ViewPaperActivity extends AppCompatActivity implements View.OnClickListener,
//        CompoundButton.OnCheckedChangeListener {

//public class ViewPaperActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
//public class DailyTestActivity extends AppCompatActivity implements OkHttpUtils.callBack{
public class DailyTestActivity extends AppCompatActivity {
//    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapterDetail mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private String astro;
    private String url1;
//    private String url2;
//    private String url3;
    private Gson gson;
    private String str1;
    private String str2;
    private String str3;

//    private CardFragmentPagerAdapter mFragmentCardAdapter;
//    private ShadowTransformer mFragmentCardShadowTransformer;

//    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);

        astro = getIntent().getExtras().getString("astro");
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        mButton = (Button) findViewById(R.id.search_button);
//        mButton = (Button) findViewById(R.id.cardTypeBtn);
//        ((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
//        mButton.setOnClickListener(this);

//        url1 = UrlUtil.start1.replace("%d",astro);
//        gson = new Gson();
//        OkHttpUtils.callBackUIDataFormatOne(url1,OkHttpUtils.TYPE_TEXT,this);

        mCardAdapter = new CardPagerAdapterDetail();
        mCardAdapter.addCardItem(new CardItemDetail(R.string.title_1, astro));
        mCardAdapter.addCardItem(new CardItemDetail(R.string.title_2, str2));
        mCardAdapter.addCardItem(new CardItemDetail(R.string.title_3, str3));

////        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));
//        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
//                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

//    @Override
//    public void onClick(View view) {
//
//    }

//    public static float dpToPixels(int dp, Context context) {
//        return dp * (context.getResources().getDisplayMetrics().density);
//    }
//
//    @Override
//    public void callBackUIString(String data) {
//         DailyAstro astroBean = gson.fromJson(data, DailyAstro.class);
//         str1 = astroBean.getSummary();
//         str2 = astroBean.getWork() + '\n' + astroBean.getMoney();
//         str3 = astroBean.getSummary();
//    }
//
//    @Override
//    public void callBackUIByte(byte[] datas) {
//
//    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        mCardShadowTransformer.enableScaling(b);
////        mFragmentCardShadowTransformer.enableScaling(b);
//    }




}