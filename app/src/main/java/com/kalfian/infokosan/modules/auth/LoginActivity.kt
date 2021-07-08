package com.kalfian.infokosan.modules.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.ActivityLoginBinding
import www.sanju.motiontoast.MotionToast

class LoginActivity : AppCompatActivity() {

    // Like the XML name activity_login to ActivityLoginBinding
    private lateinit var b: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.loginBtn.setOnClickListener {
            MotionToast.createColorToast(this,"Login success!",
                "Success Login",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, R.font.helvetica_regular)
            )
        }



    }
}