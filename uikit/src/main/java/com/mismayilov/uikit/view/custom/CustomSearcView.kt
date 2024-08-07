package com.mismayilov.uikit.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mismayilov.uikit.R
import com.mismayilov.uikit.databinding.CustomSearcviewBinding


class CustomSearcView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val binding = CustomSearcviewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSearcView)
        val hint = attributes.getString(R.styleable.CustomSearcView_hintText)
        binding.searchView.queryHint = hint
    }

    fun setOnQueryTextListener(listener: (String) -> Unit) {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listener(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listener(newText.orEmpty())
                return false
            }
        })
    }
}