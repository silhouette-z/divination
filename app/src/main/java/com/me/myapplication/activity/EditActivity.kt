package com.me.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.me.myapplication.R
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        OK.setOnClickListener {
            val intent = Intent()
            if(editText.text.toString() == "" || editText == null) {
                Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra("data",editText.text)
                setResult(RESULT_OK, intent)
                finish()
            }

        }
    }
}