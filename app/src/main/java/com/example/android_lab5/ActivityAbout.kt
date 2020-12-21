package com.example.android_lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_lab5.databinding.ActivityAboutBinding

class ActivityAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}