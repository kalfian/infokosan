package com.kalfian.infokosan.modules.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.kalfian.infokosan.databinding.ActivityRegisterBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.kalfian.infokosan.models.auth.AuthResponse;
import com.kalfian.infokosan.models.register.RegisterResponse
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.home.HomeActivity
import com.kalfian.infokosan.utils.Constant
import www.sanju.motiontoast.MotionToast

class ProfileActivity : AppCompatActivity() {
    // Like the XML name activity_login to ActivityLoginBinding
    private lateinit var b: ActivityRegisterBinding
    lateinit var sharedPref: SharedPreferences

    private lateinit var adapter: GridPropertyAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var totalPage = 10
    private var isLoad = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        //sembunyikan loader
        b.progressBar.visibility = View.INVISIBLE
        val v = b.root
        setContentView(v)
        b.submitBtn.setOnClickListener {
        }
    }
}