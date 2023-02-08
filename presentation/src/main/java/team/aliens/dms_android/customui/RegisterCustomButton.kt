package team.aliens.dms_android.customui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dms_android.R
import com.example.dms_android.databinding.RegisterCustomButtonBinding

class RegisterCustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: RegisterCustomButtonBinding

    init {
        binding = RegisterCustomButtonBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.run {
            val typedArr = context.obtainStyledAttributes(attrs, R.styleable.RegisterCustomButton)
            setMainText(typedArr.getString(R.styleable.RegisterCustomButton_first_text) ?: "")
            if (!isInEditMode) {
                typedArr.recycle()
            }
        }
    }

    private fun setMainText(text_string: String) {
        binding.tvMain.text = text_string
    }
}