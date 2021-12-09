package com.me.myapplication.activity.shake


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.me.myapplication.R
import com.me.myapplication.databinding.ResultCardBinding

import java.util.*

private var mAlertDialog: AlertDialog? = null

private val allResults = arrayOf("乾为天","坤为地", "水雷屯","山水蒙","水天需","天水讼","地水师","水地比",
    "风天小畜","天泽履","地天泰", "天地否", "天火同人", "火天大有", "地山谦", "雷地豫",
    "泽雷随", "山风蛊", "地泽临", "风地观", "火雷噬嗑", "山火贲", "山地剥", "地雷复",
    "天雷无妄", "山天大畜", "山雷颐", "泽风大过", "坎为水", "离为火", "泽山咸", "雷风恒",
    "天山遁", "雷天大壮", "火地晋", "地火明夷", "风火家人", "火泽睽", "水山蹇", "雷水解",
    "山泽损", "风雷益", "泽天夬", "天风姤", "泽地萃", "地风升", "泽水困", "水风井",
    "泽火革", "火风鼎", "震为雷", "艮为山", "风山渐", "雷泽归妹", "雷火丰", "火山旅",
    "巽为风", "兑为泽", "风水涣", "水泽节", "风泽中孚", "雷山小过", "水火既济", "火水未济")
private val random = Random()


fun Context.showResultDialog() {
    if (mAlertDialog == null || this != mAlertDialog!!.context) {//mAlertDialog里面的context如果不同，也重新new，这样避免了第一个activity用了之后，第二个activity用mAlertDialog就不显示
        mAlertDialog =
            AlertDialog.Builder(this, R.style.CustomProgressDialog).create()//去除AlertDialog的背景透明
    }

    val binding = ResultCardBinding.inflate(LayoutInflater.from(this))
    val loadView: View = binding.root

//    val loadView = LayoutInflater.from(this).inflate(R.layout.result_card, null)//默认的红色转圈
    mAlertDialog!!.setView(loadView, 0, 0, 0, 0)
    mAlertDialog!!.setCanceledOnTouchOutside(true)//随便点一下屏幕是否消失
    mAlertDialog!!.show()//显示

    val index = random.nextInt(64)
    binding.resultText.text = "卦象：" + allResults[index]
    val index1 = index + 1
    val path = "file:///android_asset/$index1.png"
    Glide.with(this)
        .load(path)
        .error(R.mipmap.error)
        .into(binding.resultImage)

    binding.bt1.setOnClickListener {
        mAlertDialog!!.dismiss()
    }

}

fun closeResultDialog(){
    if (mAlertDialog != null){
        mAlertDialog!!.dismiss()
    }
}


