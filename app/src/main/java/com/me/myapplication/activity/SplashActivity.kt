package com.me.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.me.myapplication.R



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
       // setContentView(R.layout.activity_splash)


        // 开屏持续2s
        splashScreen.setKeepVisibleCondition { !isReady() }
        // 首次使用进入设置页，
        splashScreen.setOnExitAnimationListener {
            val sp = getSharedPreferences("setting", MODE_PRIVATE)
            val isFirst = sp.getBoolean("isFirst", true)
            if (isFirst) {
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("data", FIRST_SETTING)
                startActivity(intent)
                finish()
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