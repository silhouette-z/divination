package com.me.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.CardAdapter
import com.me.myapplication.activity.adapter.PictureAdapter
import com.me.myapplication.activity.utils.LoadUtil
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_splash.*

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val picList = LoadUtil.getPictureUri(this)

        albumRecyclerView.layoutManager = GridLayoutManager(this, 3)

        albumRecyclerView.adapter = PictureAdapter(picList, this@AlbumActivity)
    }
}