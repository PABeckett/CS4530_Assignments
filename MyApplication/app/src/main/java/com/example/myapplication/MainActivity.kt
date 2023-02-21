package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    private var buttonSubmit: Button? = null
    private var textview_namebox: TextView? = null
    private var edittext_namebox: EditText? = null
    private var fullname: String? = null
    private var buttonCamera: Button? = null
    private var profPic: ImageView? = null
    private var picMap: Bitmap? = null
    private var tookPic:Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_main_backup)

        //setSupportActionBar(binding.toolbar)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)

        buttonCamera = findViewById(R.id.button_pic)
        buttonCamera!!.setOnClickListener(this)

        textview_namebox = findViewById(R.id.textview_namebox)
        edittext_namebox = findViewById(R.id.edittext_namebox)

        //edittext_namebox!!.setInputType(InputType.TYPE_CLASS_TEXT);

        ///val navController = findNavController(R.id.nav_host_fragment_content_main)
        ///appBarConfiguration = AppBarConfiguration(navController.graph)
        ///setupActionBarWithNavController(navController, appBarConfiguration)

        //binding.fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show()
        //}
    }

    override fun onClick(view: View?){
        when(view!!.id)
        {
            R.id.button_submit->
            {
                textview_namebox!!.text = ""
                fullname = edittext_namebox!!.text.toString()

                if(fullname.isNullOrBlank())
                {
                    Toast.makeText(this@MainActivity, "Please enter a name", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    fullname = fullname!!.replace("^\\s+".toRegex(), "")
                    val splitname = fullname!!.split("\\s+".toRegex()).toTypedArray()
                    Log.i("splitname", fullname!!)

                    when(splitname.size)
                    {
                        1 ->
                        {
                            Toast.makeText(
                                this@MainActivity,
                                "Must type three names, only one provided",
                                Toast.LENGTH_SHORT).show()
                        }
                        2 ->
                        {
                            Toast.makeText(
                            this@MainActivity,
                            "Must type three names, only two provided",
                            Toast.LENGTH_SHORT).show()
                        }
                        3 ->
                        {
                            //Toast.makeText(
                            //    this@MainActivity,
                            //    "Welcome",
                            //    Toast.LENGTH_SHORT).show()

                            if(tookPic == true)
                            {
                                val message_intent = Intent(this, DisplayActivity::class.java)
                                message_intent.putExtra("name", fullname)
                                message_intent.putExtra("picture", picMap)//how to pass bitmap?
                                this.startActivity(message_intent)
                            }

                            else
                            {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Must take a picture to continue",
                                    Toast.LENGTH_SHORT).show()
                            }
                            //textview_namebox!!.text = fullname
                        }
                        else ->
                        {
                            Toast.makeText(
                                this@MainActivity,
                                "Do not enter more than three names",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
            R.id.button_pic ->
            {
                //The button press should open a camera
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
            tookPic = true;

            profPic = findViewById<View>(R.id.iv_pic) as ImageView
            //val extras = result.data!!.extras
            //val thumbnailImage = extras!!["data"] as Bitmap?

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                profPic!!.setImageBitmap(thumbnailImage)
                picMap = thumbnailImage
            }
            else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                profPic!!.setImageBitmap(thumbnailImage)
                picMap = thumbnailImage
            }


        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
 */
}