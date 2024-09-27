package dev.seabat.android.composepdfviewer.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile

fun getFileInfoFromUri(context: Context, uri: Uri): Pair<String?, Long>? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.let {
        val fileName = it.name
        val fileSize = it.length()
        Pair(fileName, fileSize)
    } ?: null
}
