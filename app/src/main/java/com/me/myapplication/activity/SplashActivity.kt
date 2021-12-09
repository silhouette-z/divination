package com.me.myapplication.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.application.MyApplication
import com.me.myapplication.activity.model.CardItem
import com.me.myapplication.activity.viewModel.SettingViewModel
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class SplashActivity : AppCompatActivity() {
    companion object {
        // 开屏持续时间
        const val WORK_DURATION = 2000L

        const val FIRST_SETTING = 0
    }

    val initTime = SystemClock.uptimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)
        // 开屏持续1s
        splashScreen.setKeepVisibleCondition { !isReady() }

        val sp = getSharedPreferences("setting", MODE_PRIVATE)
        val isFirst = sp.getBoolean("isFirst", true)
        if (isFirst) {
            while (!isReady()) {

            }

            val intent = Intent(this, SettingActivity::class.java)
            startActivityForResult(intent, FIRST_SETTING)
        }

        Thread.sleep(1000)

        navView.setNavigationItemSelectedListener {
            when (it.title) {
                "版本" -> {
                    drawerLayout.closeDrawers()
                    version()
                }

                "打赏" -> {
                    drawerLayout.closeDrawers()
                    pay()
                }
                "设置" -> {
                    drawerLayout.closeDrawers()
                    setting()
                }
                else -> Log.d("aa", it.itemId.toString())
            }
            true
        }



        val cardList = initCards()

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = CardAdapter(cardList)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FIRST_SETTING -> {

            }
        }
    }

    /**
     * 设置页
     */
    fun setting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    /**
     * 初始化功能列表
     */

    fun initCards(): List<CardItem> {
        return listOf(
            CardItem("每日运势", R.drawable.ic_animal1),
            CardItem("摇一摇", R.drawable.ic_animal2),
            CardItem("每日心情", R.drawable.ic_animal3),
            CardItem("不知道", R.drawable.ic_animal4)
        )
    }

    /**
     * 打开打赏页
     */

    fun pay() {
        val intent = Intent(this, PayActivity::class.java)
        startActivity(intent)
    }

    /**
     * 打开版本页
     */
    fun version() {
        val intent = Intent(this, VersionActivity::class.java)
        startActivity(intent)
    }

    fun isReady(): Boolean {
        return SystemClock.uptimeMillis() - initTime > WORK_DURATION
    }


}