package com.kalfian.infokosan.modules.register

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

class RegisterActivity : AppCompatActivity() {
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
            var name = b.nameEdit.text.toString()
            var gender = "1"
            if (b.maleRd.isSelected)
                gender = "0"
            var phone = b.phoneEdit.text.toString()
            var ktp = b.ktpEdit.text.toString()
            var address = b.addressEdit.text.toString()
            var email = b.emailEdit.text.toString()
            var password = b.passwordEdit.text.toString()

            register(name, gender, phone, ktp, address, email, password)
        }
    }

    private fun register(
         name: String,
         gender: String,
         phone: String,
         ktp: String,
         address: String,
         email: String,
         password: String
    ) {
        isLoad = true
        //tampil loader
        b.progressBar.visibility = View.VISIBLE

        val params = HashMap<String, String>()
        params["name"] = name.toString()
        params["gender"] = gender.toString()
        params["phone"] = phone.toString()
        params["identity"] = ktp.toString()
        params["ktp"] = ktp.toString()
        params["address"] = address.toString()
        params["email"] = email.toString()
        params["role"] = Constant.ROLE_USER
        params["password"] = password.toString()

        RetrofitClient.instance.postRegister(parameters = params).enqueue(object:
            Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {

                val responses = response.body()
                Log.i("PARAM", "${password.toString()}")
                Log.i("RESPONSE_API", "${response.code()}")

                if (responses != null) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    if (responses.code == 200) {
                        MotionToast.createColorToast(this@RegisterActivity,"Berhasil!",
                            "Anda Telah Berhasil Daftar!",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@RegisterActivity, R.font.helvetica_regular)
                        )
                        startActivity(intent)
                        finish()
                    } else {
                        MotionToast.createColorToast(this@RegisterActivity,"Daftar Gagal!",
                            "Kredensial Salah !",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@RegisterActivity, R.font.helvetica_regular)
                        )
                    }
                } else {
                    MotionToast.createColorToast(this@RegisterActivity,"Ada Kesalaha!",
                        "Server Error !",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@RegisterActivity, R.font.helvetica_regular)
                    )
                }

                isLoad = false
                b.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
            }
        })
    }
}