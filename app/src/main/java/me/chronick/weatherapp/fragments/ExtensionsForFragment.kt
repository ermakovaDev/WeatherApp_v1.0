package me.chronick.weatherapp.fragments

import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permission: String): Boolean { //users permission - true or false
    Log.d("MyLog", "PERMISSION_GRANTED :  ${PackageManager.PERMISSION_GRANTED}")
    return ContextCompat.checkSelfPermission(activity as AppCompatActivity, permission) == PackageManager.PERMISSION_GRANTED

}