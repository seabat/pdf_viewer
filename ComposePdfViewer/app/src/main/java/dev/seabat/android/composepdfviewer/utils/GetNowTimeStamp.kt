package dev.seabat.android.composepdfviewer.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getNowTimeStamp(): String {
    return ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
}