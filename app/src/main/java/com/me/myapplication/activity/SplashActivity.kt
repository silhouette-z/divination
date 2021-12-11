package com.me.myapplication.activity

<<<<<<< HEAD
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
=======
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
>>>>>>> abb43fcd60d3c2b481f7e190a856056fc7da2106
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock

import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.model.CardItem
<<<<<<< HEAD

import kotlinx.android.synthetic.main.activity_splash.*


=======
import kotlinx.android.synthetic.main.activity_splash.*

>>>>>>> abb43fcd60d3c2b481f7e190a856056fc7da2106

class SplashActivity : AppCompatActivity() {
    companion object {
        // 开屏持续时间
        const val WORK_DURATION = 2000L

        const val FIRST_SETTING = 0
    }


    val initTime = SystemClock.uptimeMillis()


    @SuppressLint("ShortAlarm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, AutoReceiver::class.java)
        intent.action = "VIDEO_TIMER"
        val sender: PendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        val am = getSystemService(ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),10*1000,sender)



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
        if(resultCode == RESULT_OK) {
            when (requestCode) {
                FIRST_SETTING -> {

                }
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
            CardItem("随机鸡汤", R.drawable.ic_animal4))
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

<<<<<<< HEAD
=======
    private fun requestPermission() {
        val storagePermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, storagePermissions, 123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            Log.d("permissions:", permissions[i] + "申请结果：" + grantResults[i])
        }
    }


>>>>>>> abb43fcd60d3c2b481f7e190a856056fc7da2106
}