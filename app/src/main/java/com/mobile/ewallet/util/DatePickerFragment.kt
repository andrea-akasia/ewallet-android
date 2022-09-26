package com.mobile.ewallet.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(
    private var maxDate: String = "",
    private var minDate: String = ""
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DateListener {
        fun onDateSelected(date: String)
    }

    var listener: DateListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dialog =  DatePickerDialog(requireActivity(), this, year, month, day)

        if(maxDate.isNotEmpty()){
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(maxDate)?.let {
                dialog.datePicker.maxDate = it.time
            }
        }
        if(minDate.isNotEmpty()){
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(minDate)?.let {
                dialog.datePicker.minDate = it.time
            }
        }

        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener?.onDateSelected("${fixDay(day)}-${fixMonth(month)}-$year")
    }

    private fun fixDay(old: Int): String{
        return if(old < 10){
            "0$old"
        }else{
            old.toString()
        }
    }

    private fun fixMonth(old: Int): String{
        val real = old+1
        return if(real < 10){
            "0$real"
        }else{
            real.toString()
        }
    }
}