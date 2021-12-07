package com.me.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.me.myapplication.R

class SplashActivity : AppCompatActivity() {
    companion object{
        const val WORK_DURATION = 1000L
    }
    private val initTime = SystemClock.uptimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_splash)
        splashScreen.setKeepVisibleCondition { !isReady() }

    }

    fun isReady(): Boolean {
        return SystemClock.uptimeMillis() - initTime > WORK_DURATION
    }


}