package com.kalfian.infokosan.modules.auth

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
import com.kalfian.infokosan.databinding.ActivityLoginBinding
import com.kalfian.infokosan.databinding.FragmentHomeBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.kalfian.infokosan.models.auth.AuthResponse;
import com.kalfian.infokosan.modules.home.HomeActivity
import com.kalfian.infokosan.utils.Constant
import www.sanju.motiontoast.MotionToast

class LoginActivity : AppCompatActivity() {
    // Like the XML name activity_login to ActivityLoginBinding
    private lateinit var b: ActivityLoginBinding
    lateinit var sharedPref : SharedPreferences

    private lateinit var adapter: GridPropertyAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var email = "";
    private var password = "";
    private var totalPage = 10
    private var isLoad = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        //sembunyikan loader
        b.progressBar.visibility = View.INVISIBLE
        val v = b.root
        setContentView(v)
        var txtEmail: EditText = findViewById(R.id.email_edit)
        var txtPassword: EditText = findViewById(R.id.password_edit)

        b.loginBtn.setOnClickListener {
            email = txtEmail.text.toString();
            password = txtPassword.text.toString();
            authLogin(email, password);
        }
    }

    private fun authLogin(email: String, password: String) {
        isLoad = true
        //tampil loader
        b.progressBar.visibility = View.VISIBLE

        val params = HashMap<String, String>()
        params["email"] = email.toString().trim()
        params["password"] = password.toString().trim()

        RetrofitClient.instance.postAuth(parameters = params).enqueue(object:
                Callback<AuthResponse> {
            override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
            ) {

                val responses = response.body()?.data
                Log.i("PARAM", "${password.toString()}")
                Log.i("RESPONSE_API", "${response.code()}")

                if (responses != null) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    MotionToast.createColorToast(this@LoginActivity,"Login Berhasil!",
                            "Selamat Datang ${responses.user.name} !",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@LoginActivity, R.font.helvetica_regular)
                    )
                    setPref(responses.user.id, responses.user.email, responses.token)
                    startActivity(intent)
                    finish()
                } else {
                    MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                            "Kredensial Salah !",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@LoginActivity, R.font.helvetica_regular)
                    )
                }

                Log.i("asu", responses.toString())
                isLoad = false
                b.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
            }
        })
    }

    private fun setPref(id: Int, email: String, token: String) {
        val editor:SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(Constant.PREF_ID, id)
        editor.putString(Constant.PREF_EMAIL, email)
        editor.putString(Constant.PREF_TOKEN, token)
        editor.apply()
    }
}