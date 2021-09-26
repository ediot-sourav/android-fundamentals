package com.nullediots.empty

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder

class MainActivity : AppCompatActivity() {

    private val  CHANNEL_ID = "channelId"
    private val CHANNEL_NAME = "channelName"
        private val NOTIFICATION_ID = 0

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("Superman", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val btnSelected = findViewById<Button>(R.id.btnSelectFromGallery)
        val btnAnotherActivity = findViewById<Button>(R.id.btnAnotherActivity)
        val imageSelected = findViewById<ImageView>(R.id.imageSelected)
        val btnDialogActivity = findViewById<Button>(R.id.btn_dialog)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLoad = findViewById<Button>(R.id.btnPref)
        val btnNotification = findViewById<Button>(R.id.btnNotification)
        val etName = findViewById<EditText>(R.id.etName)

        btnSave.setOnClickListener {
            val name = etName.text.toString()

            editor.apply {
               putString("name", name)
                apply()
            }
        }

        btnLoad.setOnClickListener {
          val name = sharedPref.getString("name", null)
            etName.setText(name)
        }

       val getImage = registerForActivityResult(ActivityResultContracts.GetContent()
       ) {
           imageSelected.setImageURI(it)
       }


        btnSelected.setOnClickListener {
            getImage
                .launch("image/*")
        }
        btnAnotherActivity.setOnClickListener{
                requestPermission()
            }
        btnDialogActivity.setOnClickListener{
            Intent(this, PermissionActivity::class.java).also {
                startActivity(it)
            }
            }

        /////////////////////////////////////Notification/////////////////////////////////

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.id.search_mag_icon)
            .setContentTitle("Superman is Best")
            .setContentText("CSK will win IPl")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        btnNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun checkStoragePermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun checkForegroundLocation() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkBackgroundLocation() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestPermission(){
        val list = mutableListOf<String>()

        if (!checkStoragePermission()){
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!checkForegroundLocation()){
            list.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (checkForegroundLocation() && !checkBackgroundLocation()){
            list.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if (list.isNotEmpty()){
            ActivityCompat.requestPermissions(this, list.toTypedArray(), 0)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       if (requestCode == 0 && grantResults.isNotEmpty()){
           for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permissions", "${permissions[i]} Granted")
                }
           }
       }
    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.incscape -> Toast.makeText(this, "ChalaJaaBsdk", Toast.LENGTH_LONG)
                .show()
        }
        return true
    }


    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                    lightColor = Color.GREEN
                    enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
        }
    }

}