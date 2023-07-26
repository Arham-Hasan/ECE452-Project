package com.example.tracktor.common.functions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.isValidEmail(): Boolean{
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean{
    return this.length > 7 && this.isNotBlank()
}

fun String.passwordsMatch(repeated:String): Boolean{
    return this == repeated
}
fun convertToEST12HourTime(timestamp: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val estTimeZone = TimeZone.getTimeZone("America/New_York")

    // Parse the timestamp to a Date object
    val date = dateFormat.parse(timestamp)

    // Convert to EST time zone
    dateFormat.timeZone = estTimeZone
    val estTime = dateFormat.format(date)

    // Format the date in 12-hour time format (hh:mm a)
    val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
        .format(dateFormat.parse(estTime))

    return formattedTime
}