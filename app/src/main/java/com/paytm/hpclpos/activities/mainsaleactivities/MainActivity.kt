package com.paytm.hpclpos.activities.mainsaleactivities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.paytm.hpclpos.R

class MainActivity : AppCompatActivity() {
    var navController: NavController? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
        navController = Navigation.findNavController(this ,R.id.nav_host_fragment)
    }
}