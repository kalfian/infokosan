package com.kalfian.infokosan.modules.property

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.infokosan.databinding.ActivityDetailPropertyBinding

class DetailPropertyActivity : AppCompatActivity() {

    private lateinit var b: ActivityDetailPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailPropertyBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}