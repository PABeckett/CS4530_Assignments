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

        picBox!!.setImageBitmap(profpic)
        namebox!!.text = fullname + " is logged in!"

    }
}