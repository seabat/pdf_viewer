package dev.seabat.android.composepdfviewer.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore


/**
 * サンプル： content://com.android.providers.media.documents/document/document%3A1000000020
 */
fun getFileName(uri: Uri, context: Context): String? {
    val contentResolver: ContentResolver = context.contentResolver
    var fileName: String? = null
    if (DocumentsContract.isDocumentUri(context, uri)) {
        // DocumentProvider
        val docId = DocumentsContract.getDocumentId(uri)
        when {
            uri.isExternalStorageDocument() -> {
                val split = docId.split(":")
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    fileName = "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }
            }
            uri.isDownloadsDocument() -> {
                val id = docId.toLong()
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    id
                )
                fileName = getDataColumn(contentResolver, contentUri, null, null)
            }
            uri.isMediaDocument() -> {
                val split = docId.split(":")
                val type = split[0]
                val contentUri: Uri = when (type) {
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                fileName = getDataColumn(contentResolver, contentUri, selection, selectionArgs)
            }
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        // MediaStore (and general)
        fileName = getDataColumn(contentResolver, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        // File
        fileName = uri.path
    }
    return fileName
}

fun Uri.isExternalStorageDocument(): Boolean {
    return "com.android.externalstorage.documents" == authority
}

fun Uri.isDownloadsDocument(): Boolean {
    return "com.android.providers.downloads.documents" == authority
}

fun Uri.isMediaDocument(): Boolean {
    return "com.android.providers.media.documents" == authority
}

fun getDataColumn(
    contentResolver: ContentResolver,
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)
    try {
        cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(column_index)
        }
    } finally {
        cursor?.close()
    }
    return null
}