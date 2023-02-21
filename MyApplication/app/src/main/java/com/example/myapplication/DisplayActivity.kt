package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayActivity : AppCompatActivity() {

    private var namebox: TextView? = null
    private var picBox: ImageView? = null
    private var fullname: String? = null
    private var profpic: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        namebox = findViewById(R.id.namebox)
        picBox = findViewById(R.id.picbox)

        val receivedIntent = intent

        fullname = receivedIntent.getStringExtra("name")
        profpic = receivedIntent.getParcelableExtra ("picture", Bitmap::class.java)

        fullname = fullname!!.replace("^\\s+".toRegex(), "")
        val splitname = fullname!!.split("\\s+".toRegex()).toTypedArray()

        val firstlast = splitname[0] + " " + splitname[2]

        //picbox is null, intent is ok
        picBox!!.setImageBitmap(profpic)//null here? profpic not passed correctly
        namebox!!.text = firstlast

    }
}