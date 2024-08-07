package com.mismayilov.uikit.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mismayilov.uikit.R
import com.mismayilov.uikit.databinding.CustomSpinnerBinding

class CustomSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = CustomSpinnerBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Spinner)
        val title = typedArray.getString(R.styleable.Spinner_spinner_text)
        binding.spinnerText.text = title
        typedArray.recycle()
    }

    fun showError(title: String) {
        (binding.spinner.selectedView as? TextView)?.error = title
    }

    fun clearError() {
        (binding.spinner.selectedView as? TextView)?.error = null
    }

    fun setSpinnerValues(values: MutableList<String>) {
        values.add(0, context.getString(R.string.select))
        binding.spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, values)
    }

    fun getSelectedItem(): String {
        return binding.spinner.selectedItem.toString()
    }

    fun setSelection(position: Int) {
        binding.spinner.setSelection(position + 1)
    }

    fun setOnItemSelectedListener(listener: (Int, String) -> Unit) {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                view?.let {
                    listener(position, getSelectedItem())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    fun getSelectedItemPosition(): Int {
        return binding.spinner.selectedItemPosition
    }
}