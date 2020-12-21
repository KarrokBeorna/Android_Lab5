package com.example.android_lab5

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Activity1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)
    }

    fun toSecond(view: View) {
        startActivity(Intent("com.example.android_lab5.Activity2"))
    }
}