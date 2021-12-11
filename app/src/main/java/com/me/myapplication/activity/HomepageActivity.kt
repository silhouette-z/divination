package com.me.myapplication.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.model.CardItem
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
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

        val intent = Intent(this, AutoReceiver::class.java)
        intent.action = "VIDEO_TIMER"
        val sender: PendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        val am = getSystemService(ALARM_SERVICE) as AlarmManager
        am.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),10*1000,sender)


        val cardList = initCards()

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = CardAdapter(cardList)
    }

    /**
     * 打开设置页
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
            CardItem("随机鸡汤", R.drawable.ic_animal4),
            CardItem("测夫妻相", R.drawable.ic_animal5)
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
}