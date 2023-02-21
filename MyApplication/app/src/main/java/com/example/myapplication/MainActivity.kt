package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    private var buttonSubmit: Button? = null
    //private var textview_namebox: TextView? = null
    private var firstname: EditText? = null
    private var middlename: EditText? = null
    private var lastname: EditText? = null
    private var fullname: String? = null
    private var buttonCamera: Button? = null
    private var profPic: ImageView? = null
    private var picMap: Bitmap? = null
    private var tookPic:Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)

        buttonCamera = findViewById(R.id.button_pic)
        buttonCamera!!.setOnClickListener(this)

        firstname = findViewById(R.id.firstname)
        middlename = findViewById(R.id.middlename)
        lastname = findViewById(R.id.lastname)

        profPic = findViewById<View>(R.id.iv_pic) as ImageView

        if(savedInstanceState != null)
        {
            picMap = savedInstanceState!!.getParcelable("picture", Bitmap::class.java)
            profPic!!.setImageBitmap(picMap)
        }
    }

    override fun onClick(view: View?){
        when(view!!.id)
        {
            R.id.button_submit->
            {
                if(firstname!!.text.isNullOrBlank() || lastname!!.text.isNullOrBlank())
                {
                    Toast.makeText(this@MainActivity, "Please enter at least a first and last name", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    fullname = firstname!!.text.toString() + " " + lastname!!.text.toString()

                    if(picMap != null)
                    {
                        val message_intent = Intent(this, DisplayActivity::class.java)
                        message_intent.putExtra("name", fullname)
                        message_intent.putExtra("picture", picMap)
                        this.startActivity(message_intent)
                    }

                    else
                    {
                        Toast.makeText(
                            this@MainActivity,
                            "Must take a picture to continue",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.button_pic ->
            {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex: ActivityNotFoundException){
                    //Do error handling here
                }
            }
        }



    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {

            profPic = findViewById<View>(R.id.iv_pic) as ImageView

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                profPic!!.setImageBitmap(thumbnailImage)
                picMap = thumbnailImage
            }
            else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                profPic!!.setImageBitmap(thumbnailImage)
                picMap = thumbnailImage
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(picMap != null)
        {
            outState.putParcelable("picture", picMap)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        picMap = savedInstanceState!!.getParcelable("picture", Bitmap::class.java)
        profPic!!.setImageBitmap(picMap)
    }
}