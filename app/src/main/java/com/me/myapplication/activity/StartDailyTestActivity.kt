package com.me.myapplication.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.me.myapplication.R
import okhttp3.*


class StartDailyTestActivity : AppCompatActivity() {
    private var mButton: ImageButton? = null
    private var mInfo: EditText? = null
    var astro: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_daily_test)
        mButton = findViewById<View>(R.id.search_button) as ImageButton
        mInfo = findViewById<View>(R.id.start_search_info) as EditText

        mButton!!.setOnClickListener {
            val intent = Intent(this@StartDailyTestActivity, DailyTestActivity::class.java)
            val bundle = Bundle()
            astro = mInfo!!.text.toString()

            bundle.putString("astro", astro)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

}