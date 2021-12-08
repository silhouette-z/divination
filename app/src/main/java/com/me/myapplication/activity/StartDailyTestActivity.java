package com.me.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.me.myapplication.R;


import androidx.appcompat.app.AppCompatActivity;



public class StartDailyTestActivity extends AppCompatActivity{

    private ImageButton mButton;
    private EditText mInfo;
    String astro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_daily_test);
        mButton = (ImageButton) findViewById(R.id.search_button);
        mInfo = (EditText) findViewById(R.id.start_search_info);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartDailyTestActivity.this, DailyTestActivity.class);
                Bundle bundle = new Bundle();
                astro = mInfo.getText().toString();
                bundle.putString("astro",astro);
                intent.putExtras(bundle);

            startActivity(intent);
            }
        });

    }
}
