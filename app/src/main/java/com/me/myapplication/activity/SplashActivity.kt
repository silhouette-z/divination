package com.me.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.me.myapplication.R
import kotlinx.android.synthetic.main.activity_splash.*

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
            when(it.itemId) {
                2131230734 -> {
                    drawerLayout.closeDrawers()
                    version()
                }

                2131231129 -> {
                    drawerLayout.closeDrawers()
                    pay()
                }
                else -> Log.d("aa", it.itemId.toString())
            }
                true
        }
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