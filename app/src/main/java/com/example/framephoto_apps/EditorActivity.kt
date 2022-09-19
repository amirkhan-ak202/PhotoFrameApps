package com.example.framephoto_apps

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.impulsive.zoomimageview.ZoomImageView

class EditorActivity : AppCompatActivity(), Onclick{
    private lateinit var frameContainer:ImageView
    private lateinit var button:Button
    private lateinit var userimage :ZoomImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var screenshot:RelativeLayout
    private lateinit var list:MutableList<Framelist>
    private val drw = arrayOf(
      R.drawable.nw ,R.drawable.k5,R.drawable.k6, R.drawable.k3, R.drawable.k2, R.drawable.k7,R.drawable.k4
    ,R.drawable.pngtree,R.drawable.kidnipping)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        val path= intent.getStringExtra("path")

        frameContainer = findViewById(R.id.frame_container);
        button = findViewById(R.id.savebtn)
        userimage = findViewById(R.id.user_image)
        recyclerView=findViewById(R.id.frame_recycler)
        screenshot=findViewById(R.id.screenshot)


        Glide.with(this).load(path).into(userimage)
        list= ArrayList()

        recyclerView.adapter= FrameAdapter(this,list,this)

        button.setOnClickListener {
            store(getscreeshot(screenshot))

        }


        intilize()

    }

    private fun intilize() {
        for (j in drw)
        {
            list.add(Framelist(j))

        }

    }

    override fun clickFrame(position:Int) {
        Glide.with(this).load(list[position].drawb).into(frameContainer)

    }

    private fun getscreeshot(view:View) : Bitmap{
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.getDrawingCache())

        view.isDrawingCacheEnabled = false
        return bitmap
    }
    private fun store(bitmap: Bitmap){
        var uri:Uri? =null;

        if (SDK_INT >=Build.VERSION_CODES.R)
            uri= MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentvalues= ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME,"displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE,"image/jpg")
            put(MediaStore.Images.Media.WIDTH,bitmap.width)
            put(MediaStore.Images.Media.HEIGHT,bitmap.height)


        }
       try {

            val u = contentResolver.insert(uri,contentvalues)
            val outputStream = contentResolver.openOutputStream(u!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
           Toast.makeText(this,"Image Saved",Toast.LENGTH_LONG).show()
       }
       catch (e:Exception){
           Toast.makeText(this,"Image Not Saved",Toast.LENGTH_LONG).show()

       }


    }




}