package com.nullediots.empty

import android.Manifest
import android.content.Intent

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelected = findViewById<Button>(R.id.btnSelectFromGallery)
        val btnAnotherActivity = findViewById<Button>(R.id.btnAnotherActivity)
        val imageSelected = findViewById<ImageView>(R.id.imageSelected)
        val btnDialogActivity = findViewById<Button>(R.id.btn_dialog)

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


}