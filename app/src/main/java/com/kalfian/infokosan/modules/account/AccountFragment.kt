package com.kalfian.infokosan.modules.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.FragmentAccountBinding
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.home.HomeActivity
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.customDialog


class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var b: FragmentAccountBinding
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentAccountBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)
        b.name.text = sharedPref.getString(Constant.PREF_NAME, "")
        b.loc.text = sharedPref.getString(Constant.PREF_LOCATION, "")
        b.phone.text = sharedPref.getString(Constant.PREF_EMAIL, "")

        b.btnLogout.setOnClickListener {
            customDialog(requireContext(),
                "Apakah anda yakin ingin keluar?",
                "tekan tombol keluar untuk kembali ke halaman login", "Keluar", "Batal"
            ) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                // RESET SHARED PREF
                sharedPref.edit().clear().apply()

                startActivity(intent)
                requireActivity().finish()
            }
        }

        b.btnInfo.setOnClickListener{
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
