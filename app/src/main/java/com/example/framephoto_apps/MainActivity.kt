package com.example.framephoto_apps

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
  private  val persmission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkpermission()

        findViewById<Button>(R.id.select_image).setOnClickListener {
            val intent =Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            val uri = data!!.data

            val filepath= arrayOf(MediaStore.Images.Media.DATA)
            val c= contentResolver.query(uri!!,filepath,null,null,null)

            c!!.moveToNext()
            val columnindex= c.getColumnIndex(filepath[0])
            val pictpath = c.getString(columnindex)
            c.close()

            //picture will be open in new activity
            val intent= Intent(this,EditorActivity::class.java)
            intent.putExtra("path",pictpath)
            startActivity(intent)




        }
    }

    //check persmission to allow or not
    private fun checkpermission() : Boolean
    {
        var result:Int
        var listpersmissionneed: MutableList<String> =ArrayList()

        for (p in persmission)
        {
            result = ContextCompat.checkSelfPermission(this,p)

            if (result != PackageManager.PERMISSION_GRANTED)
            {
                listpersmissionneed.add(p)
            }

        }
        if (listpersmissionneed.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this,listpersmissionneed.toTypedArray(),0)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==0){
            if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"please allow all permission",Toast.LENGTH_LONG).show()

            }
        }
    }
}