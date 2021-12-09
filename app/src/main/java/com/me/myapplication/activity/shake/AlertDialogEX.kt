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

    val allDetails = arrayOf(getString(R.string.detail1), getString(R.string.detail2), getString(R.string.detail3),
        getString(R.string.detail4), getString(R.string.detail5), getString(R.string.detail6),
        getString(R.string.detail7), getString(R.string.detail8), getString(R.string.detail9),
        getString(R.string.detail10), getString(R.string.detail11), getString(R.string.detail12),
        getString(R.string.detail13), getString(R.string.detail14), getString(R.string.detail15),
        getString(R.string.detail16), getString(R.string.detail17), getString(R.string.detail18),
        getString(R.string.detail19), getString(R.string.detail20), getString(R.string.detail21),
        getString(R.string.detail22), getString(R.string.detail23), getString(R.string.detail24),
        getString(R.string.detail25), getString(R.string.detail26), getString(R.string.detail27),
        getString(R.string.detail28), getString(R.string.detail29), getString(R.string.detail30),
        getString(R.string.detail31), getString(R.string.detail32), getString(R.string.detail33),
        getString(R.string.detail34), getString(R.string.detail35), getString(R.string.detail36),
        getString(R.string.detail37), getString(R.string.detail38), getString(R.string.detail39),
        getString(R.string.detail40), getString(R.string.detail41), getString(R.string.detail42),
        getString(R.string.detail43), getString(R.string.detail44), getString(R.string.detail45),
        getString(R.string.detail46), getString(R.string.detail47), getString(R.string.detail48),
        getString(R.string.detail49), getString(R.string.detail50), getString(R.string.detail51),
        getString(R.string.detail52), getString(R.string.detail53), getString(R.string.detail54),
        getString(R.string.detail55), getString(R.string.detail56), getString(R.string.detail57),
        getString(R.string.detail58), getString(R.string.detail59), getString(R.string.detail60),
        getString(R.string.detail61), getString(R.string.detail62), getString(R.string.detail63),
        getString(R.string.detail64) )

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
    binding.resultText.text = allResults[index]
    binding.detailText.text = allDetails[index]

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


