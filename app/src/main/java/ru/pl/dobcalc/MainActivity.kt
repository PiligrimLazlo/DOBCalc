package ru.pl.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    private var tvDateHint: TextView? = null
    private var tvMinutesPassedHint: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvDateHint = findViewById(R.id.tvDateHint)
        tvMinutesPassedHint = findViewById(R.id.tvMinutesPassedHint)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(
                    this,
                    "год: $selectedYear, месяц: ${selectedMonth + 1}, день: $selectedDay",
                    Toast.LENGTH_LONG
                ).show()

                val selectedMonthFormatted: String =
                    if (selectedMonth <= 9) "0${selectedMonth + 1}" else "${selectedMonth + 1}"
                val selectedDayFormatted: String =
                    if (selectedDay <= 9) "0${selectedDay}" else "$selectedDay"
                val selectedDateString =
                    "$selectedDayFormatted.$selectedMonthFormatted.$selectedYear"
                tvSelectedDate?.text = selectedDateString

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
                val selectedDate = sdf.parse(selectedDateString)
                //выполнится, только если не null
                selectedDate?.let {
                    val selectedDateInMinutes = selectedDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //выполнится, только если не null
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
        tvDateHint?.visibility = TextView.VISIBLE
        tvMinutesPassedHint?.visibility = TextView.VISIBLE
    }
}