package dev.seabat.android.composepdfviewer.utils

import android.content.Context

fun listFilesInDirectory(context: Context): List<String>? {
    val directory = context.filesDir

    if (!directory.exists() || !directory.isDirectory) {
        throw IllegalStateException("指定されたディレクトリが存在しないか、ディレクトリではありません。")
        return null
    }

    val files = directory.listFiles()

    val fileNames: ArrayList<String> = arrayListOf()

    if (files != null) {
        for (file in files) {
            if (file.isFile) {
                fileNames.add(file.name)
            } else if (file.isDirectory) {
                throw IllegalStateException("指定されたディレクトリが存在しないか、ディレクトリではありません。")
            }
        }
    }
    return fileNames
}




