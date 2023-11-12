package dev.seabat.android.composepdfviewer.utils

import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun getFileTimeStamp(file: File): ZonedDateTime {
    val lastModifiedMillis = file.lastModified()
    val instant = Instant.ofEpochMilli(lastModifiedMillis)
    val zoneId = ZoneId.of("Asia/Tokyo")
    return instant.atZone(zoneId)
}