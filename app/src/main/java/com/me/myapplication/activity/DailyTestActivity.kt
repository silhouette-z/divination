package com.me.myapplication.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.TestLooperManager

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import com.google.gson.GsonBuilder
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardPagerAdapterDetail
import com.me.myapplication.activity.bean.DailyAstro
import com.me.myapplication.activity.interceptor.TimeConsumeInterceptor
import com.me.myapplication.activity.model.CardItemDetail
import com.me.myapplication.activity.model.ShadowTransformer
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class DailyTestActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mCardAdapter: CardPagerAdapterDetail? = null
    private var mCardShadowTransformer: ShadowTransformer? = null
    private var astro: String? = null
    private var textView: TextView? = null

    //    private String url2;
    //    private String url3;

    var str1: String? = null
    var str2: String? = null
    var str3: String? = null

    var mcontext: Context? = null
    var cache: Cache? = null

    val okhttpListener = object : EventListener() {}

    val gson = GsonBuilder().create()
    var client: OkHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_daily_test)
            textView = findViewById(R.id.today_info)
//            str1 = "信息每天都有更新~\n每天都记得回来看看哦~"
//            str2 = "如果你向神求助，说明你相信神的能力。如果神没有帮助你，说明神相信你的能力。"
            str1 = "今天会开心~"
            str2 = "每天都是100分~"

            astro = intent.extras!!.getString("astro")

            mcontext = applicationContext

            client = OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(TimeConsumeInterceptor())
                .eventListener(okhttpListener)
                .build()

            click()

            mViewPager = findViewById<View>(R.id.viewPager) as ViewPager
            mCardAdapter = CardPagerAdapterDetail()
            mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_1, str1))
            mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_2, str2))
//            mCardAdapter!!.addCardItem(CardItemDetail(R.string.title_3, str3))
            mCardShadowTransformer = ShadowTransformer(mViewPager, mCardAdapter)
            mViewPager!!.adapter = mCardAdapter
            mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
            mViewPager!!.offscreenPageLimit = 3
        }

        fun request(url: String, callback: Callback) {
            val request: Request = Request.Builder()
                .cacheControl(CacheControl.Builder().maxStale(10, TimeUnit.DAYS).build())
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
                    runOnUiThread {
                        if (dailyAstro.all == null) {
                            textView?.text = "您的星星可能走丢了哦~"
                        } else {
                            textView?.text = "${astro}的您今天运势如下:\n\n" +
                                    "${dailyAstro.summary}\n" +
                                    "今天总分是${dailyAstro.all}"
                        }
                    }
                }
            })
        }
    }
