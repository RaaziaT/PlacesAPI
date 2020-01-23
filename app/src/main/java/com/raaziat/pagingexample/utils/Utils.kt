package com.raaziat.pagingexample.utils

import android.content.Context
import android.widget.Toast
import kotlin.math.round
import com.raaziat.pagingexample.model.openweather.Main
import com.raaziat.pagingexample.model.openweather.X
import java.text.SimpleDateFormat


fun toast(status: String, context: Context) {
    Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
}

fun getCelsius(degreesKelvin: Double): Double {
    return (degreesKelvin - 273.16).round(0)
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun formatList(list: List<X>): List<X> {
    val listsFormatted = arrayListOf<X>()
    val listSize = list.size -1
    for (i in 0..listSize step 8) {
        val main = Main(
            list[i].main.feels_like, list[i].main.grnd_level,
            list[i].main.humidity, list[i].main.pressure, list[i].main.sea_level,
            list[i].main.temp, getCelsius(list[i].main.temp_kf),
            getCelsius(list[i].main.temp_max),
            getCelsius(list[i].main.temp_min)
        )
        val listTemp = X(
            list[i].clouds,
            list[i].dt,
            list[i].dt_txt,
            main,
            list[i].sys,
            list[i].weather,
            list[i].wind
        )
        listsFormatted.add(listTemp)
    }
    return listsFormatted
}

fun formatDay(dt: Long?): String {
    val time = java.util.Date(dt as Long * 1000)

    val simpleDateFormat = SimpleDateFormat("EEE, MMM d, ''yy")
    val newstring = simpleDateFormat.format(time)
    return newstring.substring(0, 3)
}



