package com.nullediots.empty

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class PermissionActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_permission)

        val button = findViewById<Button>(R.id.btnYesNoDialog)
        val single = findViewById<Button>(R.id.btnSingleDialog)
        val multi = findViewById<Button>(R.id.btnMultiDialog)
        val spinner = findViewById<Spinner>(R.id.spinner_gobar)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.getOrCreateBadge(R.id.home).isVisible = true

        val yesNoDialog = AlertDialog.Builder(this)
            .setTitle("Bsdk")
            .setMessage("Chala Jaa Bsdk")
            .setPositiveButton("Yes"){ _, _ ->
                Toast.makeText(this, "You Are Chodumal", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No"){_, _ ->
                Toast.makeText(this, "You Are Not A Chodumal", Toast.LENGTH_LONG).show()
            }.create()

        button.setOnClickListener {
           yesNoDialog.show()
        }


        val options = arrayOf("Gandu, Bokachoda, Suar K Bacha")
        val singleDialog = AlertDialog.Builder(this)
            .setTitle("Jhant K Baal")
            .setSingleChoiceItems(options, 0){_, i ->
                Toast.makeText(this , "You selected ${options[i]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(this, "You Accpetd that you are Jhant k Baal ", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No"){_, _ ->
                Toast.makeText(this, "You Declined that you are Jhant k Baal ", Toast.LENGTH_LONG).show()
            }.create()


        single
            .setOnClickListener {
                singleDialog.show()
            }

        val multidialog = AlertDialog.Builder(this)
            .setTitle("MultiChodu")
            .setMultiChoiceItems(options, booleanArrayOf(false, false, false)) {_ , i, selectedOptions ->

                if(selectedOptions) Toast.makeText(this , "You selected ${options[i]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(this, "You Accpetd that you all are jhant k Baal ", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No"){_, _ ->
                Toast.makeText(this, "You Declined that you all  are Jhant k Baal ", Toast.LENGTH_LONG).show()
            }.create()


        multi.setOnClickListener {
            multidialog
                .show()
        }

        val customList = listOf<String>("superman", "batman", "wonder woman", "cyborg", "flash")
        val adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, customList)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, selectedId: Long) {
                    Toast.makeText(this@PermissionActivity,
                       " ${adapterView?.getItemAtPosition(position).toString()}", Toast.LENGTH_LONG
                    ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

//    private fun checkStorage()=ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//
//    private fun checkForegroundPermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun checkBackgroundPermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun requestPermission(){
//        val permissionList = mutableListOf<String>()
//
//        if (!checkStorage()){
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//
//        if (!checkForegroundPermission()){
//            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
//        }
//
//        if (checkForegroundPermission() && !checkBackgroundPermission()){
//            permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }
//
//        if (permissionList.isNotEmpty()){
//            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 0)
//
//        }
//
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 0 && grantResults.isNotEmpty()){
//            for (i in grantResults.indices){
//                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
//                    Log.d("permissionRequest", "${permissions[i]} granted")
//                }
//            }
//        }
//    }
}

