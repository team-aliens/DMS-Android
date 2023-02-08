package team.aliens.dms_android.feature.register.ui.last.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import team.aliens.presentation.R
import team.aliens.presentation.databinding.DialogSignUpBinding

class GoLoginDialog(
    private val context: Context,
    private val onYesClick: () -> Unit = {},
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogSignUpBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.dialog_sign_up,
            null,
            false)
        dialog.setContentView(binding.root)

        binding.tvGoLogin.setOnClickListener {
            onYesClick()
            dialog.dismiss()
        }
        dialog.show()
    }
}