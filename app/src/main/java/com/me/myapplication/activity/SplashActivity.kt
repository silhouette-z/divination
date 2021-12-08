package com.me.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.model.CardItem
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.nav_header.view.*

class SplashActivity : AppCompatActivity() {
    companion object{
        // 开屏持续时间
        const val WORK_DURATION = 1000L
    }
    private val initTime = SystemClock.uptimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)
        // 开屏持续1s
        splashScreen.setKeepVisibleCondition { !isReady() }
//        navView.setCheckedItem(R.id.nav)
        navView.setNavigationItemSelectedListener {
            when(it.title) {
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

    fun initCards() :List<CardItem>{
        return listOf(
            CardItem("每日运势", R.drawable.ic_animal1),
            CardItem("摇一摇", R.drawable.ic_animal2),
            CardItem("每日心情", R.drawable.ic_animal3),
            CardItem("不知道", R.drawable.ic_animal4))
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