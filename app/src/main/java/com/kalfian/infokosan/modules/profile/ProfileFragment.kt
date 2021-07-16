package com.kalfian.infokosan.modules.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.FragmentAccountBinding
import com.kalfian.infokosan.databinding.FragmentProfileBinding
import com.kalfian.infokosan.models.auth.AuthResponse
import com.kalfian.infokosan.models.profile.ProfileResponse
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.home.HomeActivity
import com.kalfian.infokosan.modules.register.RegisterActivity
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.RetrofitClient
import com.kalfian.infokosan.utils.customDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast

class ProfileFragment : Fragment() {
    private lateinit var b: FragmentProfileBinding
    lateinit var sharedPref : SharedPreferences
    private var isLoad = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentProfileBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)

        b.submitBtn.setOnClickListener {

        }
        /*b.name.text = sharedPref.getString(Constant.PREF_NAME, "")
        b.loc.text = sharedPref.getString(Constant.PREF_LOCATION, "")
        b.phone.text = sharedPref.getString(Constant.PREF_EMAIL, "")

        */
    }

    private fun getProfile() {
        isLoad = true
        //tampil loader
        b.progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getProfile().enqueue(object:
            Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {

                val responses = response.body()?.data
                Log.i("RESPONSE_API", "${response.body()}")

                if (responses != null) {
                    /*MotionToast.createColorToast(this@LoginActivity,"Login Berhasil!",
                        "Selamat Datang ${responses.user.name} !",
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@HomeActivity, R.font.helvetica_regular)
                    )*/

                } else {
                   /* MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                        "Kredensial Salah !",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@LoginActivity, R.font.helvetica_regular)
                    )*/
                }

                isLoad = false
                b.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
            }
        })
    }
}