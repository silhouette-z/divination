package com.me.myapplication.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import com.me.myapplication.R
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView
import jp.co.recruit_mp.android.lightcalendarview.MonthView
import jp.co.recruit_mp.android.lightcalendarview.accent.Accent
import jp.co.recruit_mp.android.lightcalendarview.accent.DotAccent
import java.text.SimpleDateFormat
import java.util.*


class MoodActivity : AppCompatActivity() {
    lateinit var calendarView: LightCalendarView
    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
//    private val month = mapOf(0 to "January", 1 to "February", 2 to "March", 3 to "April",4 to "May" ,
//        5 to "June", 6 to "July",7  to "August" , 8 to "September" ,9 to "October" ,10 to "November",11 to "December"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)
        val monthTitle=findViewById(R.id.month_title) as? TextView ?: throw IllegalStateException("monthtitleView not found")
        val calendarView = findViewById(R.id.calendarView) as? LightCalendarView ?: throw IllegalStateException("calendarView not found")
        val moodTop = findViewById(R.id.moodTop) as? TextView ?: throw IllegalStateException("moodtext View not found")
        val moodChoose = findViewById(R.id.moodChoose) as? RadioGroup ?: throw IllegalStateException("moodtext View not found")
        val map = mutableMapOf<Date, Collection<Accent>>()
        var moodDot=Color.parseColor("#FF4081")

//        var cale: Calendar? = null
//        cale = getInstance()
//        var monthEnglish=month[cale.get(Calendar.MONTH)]
//        monthTitle.setText(monthEnglish)
//        Log.i("month",monthTitle.text.toString())


        calendarView.setOnStateUpdatedListener(object : LightCalendarView.OnStateUpdatedListener {
            override fun onDateSelected(date: Date) {
                moodTop.visibility= View.VISIBLE
                moodChoose.visibility=View.VISIBLE
                moodChoose.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, num ->
                    when(num){
                        2131231236->moodDot= Color.parseColor("#FF4081")
                        2131231237->moodDot= Color.parseColor("#3F51B5")
                        2131231238->moodDot= Color.parseColor("#696969")
                    }

                    Log.i("click","${date}")
                    map.apply {
                        val accents = (0..0).map { DotAccent(10f, moodDot,key = "${formatter.format(date)}-${it}") }
                        put(date, accents) }
                })
                //Log.i("MainActivity", "onDateSelected: date = ${date}")


            }

            override fun onMonthSelected(date: Date, view: MonthView) {
                moodTop.visibility= View.GONE
                moodChoose.visibility=View.GONE
                title = formatter.format(date)
                monthTitle.setText(title)

//                    val cal = Calendar.getInstance()
//                    val dates = (1..31).filter { it % 2 == 0 }.map {
//                        cal.apply {
//                            set(view.month.year + 1900, view.month.month, it)
//                        }.time
//                    }
//                    map.apply {
//                        dates.forEach { date ->
//                            val accents = (0..date.date % 3).map { DotAccent(10f, key = "${formatter.format(date)}-${it}") }
//                            put(date, accents)
//                        }
//                    }
                    view.setAccents(map)

            }
        })
    }
}