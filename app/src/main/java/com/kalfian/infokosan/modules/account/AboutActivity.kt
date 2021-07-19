package com.kalfian.infokosan.modules.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.infokosan.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var b: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAboutBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.backButton.setOnClickListener {
            finish()
        }
    }
}