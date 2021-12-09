package com.me.myapplication.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import com.google.gson.GsonBuilder
import com.me.myapplication.R
import com.me.myapplication.activity.SettingActivity.Companion.CONSTELLATION
import com.me.myapplication.activity.adapter.CardItemDetail
import com.me.myapplication.activity.adapter.CardPagerAdapterDetail
import com.me.myapplication.activity.bean.DailyAstro
import com.me.myapplication.activity.interceptor.TimeConsumeInterceptor
import com.me.myapplication.activity.model.ShadowTransformer
import okhttp3.*
import java.io.IOException


class DailyTestActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mCardAdapter: CardPagerAdapterDetail? = null
    private var mCardShadowTransformer: ShadowTransformer? = null
    private var astro: String? = null
    private val url1: String? = null

    //    private String url2;
    //    private String url3;

    var str1: String? = null
    var str2: String? = null
    var str3: String? = null

    var mcontext: Context? = null

    val okhttpListener = object : EventListener() {
        override fun dnsStart(call: Call, domainName: String) {
            super.dnsStart(call, domainName)
//            showText?.text = showText?.text.toString() + "\nDns Search:" + domainName
        }

        override fun responseBodyStart(call: Call) {
            super.responseBodyStart(call)
//            showText?.text = showText?.text.toString() + "\nResponse Start"
        }
    }
    val gson = GsonBuilder().create()
    var client : OkHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_test)

        //astro = intent.extras!!.getString("astro")
        astro = getSharedPreferences("setting", MODE_PRIVATE).getString(CONSTELLATION,"")

        mcontext = applicationContext

        client = OkHttpClient
            .Builder()
            .addInterceptor(TimeConsumeInterceptor())
            .eventListener(okhttpListener)
            .build()

        click()

        str1 = "wssss"
        str2 = "yes"
        mViewPager = findViewById<View>(R.id.viewPager) as ViewPager
        mCardAdapter = CardPagerAdapterDetail()
        mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_1, astro))
        mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_2, str1))
        mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_3, str3))
        mCardShadowTransformer = ShadowTransformer(mViewPager, mCardAdapter)
        mViewPager!!.adapter = mCardAdapter
        mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
        mViewPager!!.offscreenPageLimit = 3
    }

    fun request(url: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client?.newCall(request)?.enqueue(callback)
    }

    fun click() {
        val url =
            "http://web.juhe.cn/constellation/getAll?consName=${astro}&type=today&key=994c7f89c7d8012fe43ae2cd2d736302"

        request(url, object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                str1 = e.message
            }

            override fun onResponse(call: Call, response: Response) {

                val bodyString = response.body?.string()
                val dailyAstro = gson.fromJson(bodyString, DailyAstro::class.java)
                runOnUiThread{
                    if (dailyAstro.all == null){
                        str1 = "no answer"}
                    else{
                        str1 = dailyAstro.summary
                        str2 = "test"
                    }
                }
            }
        })
    }
}