package com.me.myapplication.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock

import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.model.CardItem


import kotlinx.android.synthetic.main.activity_splash.*

import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    companion object {
        // 开屏持续时间
        const val WORK_DURATION = 1000L

        const val FIRST_SETTING = 0
    }

    val initTime = SystemClock.uptimeMillis()


    @SuppressLint("ShortAlarm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)


        // 开屏持续1s
        splashScreen.setKeepVisibleCondition { !isReady() }

        splashScreen.setOnExitAnimationListener {
            val sp = getSharedPreferences("setting", MODE_PRIVATE)
            val isFirst = sp.getBoolean("isFirst", true)
            if (isFirst) {
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("data", FIRST_SETTING)
                startActivity(intent)
            } else {
                val intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun isReady(): Boolean {
        return SystemClock.uptimeMillis() - initTime > WORK_DURATION
    }



}