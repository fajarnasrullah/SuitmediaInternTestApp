package com.jer.suitmediainterntestapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.jer.suitmediainterntestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navhost_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

//        navController.navigate(R.id.palindromeFragment)
    }
}