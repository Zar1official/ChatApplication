package ru.zar1official.chatapplication.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    private const val formatPattern = "dd.MM.yyyy HH:mm"

    @SuppressLint("SimpleDateFormat")
    fun millisToDateTime(millis: Long): String =
        SimpleDateFormat(formatPattern).format(Date(millis))
}