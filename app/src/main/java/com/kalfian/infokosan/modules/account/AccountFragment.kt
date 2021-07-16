package com.kalfian.infokosan.modules.account

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.FragmentAccountBinding
import com.kalfian.infokosan.modules.auth.LoginActivity


class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var b: FragmentAccountBinding

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

        b.btnLogout.setOnClickListener {
            customDialog(requireContext(), "Keluar", "Batal") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}

fun customDialog(ctx: Context, okTitle: String, cancelTitle: String, btnOkOnClick: View.OnClickListener) {
    val dialog = Dialog(ctx)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(R.layout.custom_modal)


    val btnCancel = dialog.findViewById<Button>(R.id.btn_modal_batal)
    val btnOk = dialog.findViewById<Button>(R.id.btn_modal_ok)

    btnCancel.text = cancelTitle
    btnOk.text = okTitle

    btnOk.setOnClickListener(btnOkOnClick)
    btnCancel.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}