package br.com.alura.sevendaysofcodeandroid.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toBrazilianDateTime(
    dateTimePattern: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"
): String? {
    return SimpleDateFormat(
        "dd/MM/yyyy HH:dd:ss",
        Locale.getDefault()
    ).let { formatter ->
        SimpleDateFormat(
            dateTimePattern,
            Locale.getDefault()
        ).parse(this)?.let {
            formatter.format(it)
        }
    }
}