package com.kalfian.infokosan

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.home.HomeActivity
import com.kalfian.infokosan.utils.Constant

class SplashScreenActivity : AppCompatActivity() {

    // Add splashscreen time to 1 second
    private val SPLASH_TIME: Long = 1000
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            sharedPref = this.getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)
            val userID = sharedPref.getInt(Constant.PREF_ID, 0)

            if (userID == 0) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIME)

    }
}