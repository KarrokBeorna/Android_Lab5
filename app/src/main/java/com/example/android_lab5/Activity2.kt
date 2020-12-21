package com.example.android_lab5

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Activity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
    }

    fun toFirst(view: View) {
        finish()
    }

    fun toThird(view: View) {
        startActivity(Intent("com.example.android_lab5.Activity3"))
    }
}