package dev.seabat.android.composepdfviewer

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.seabat.android.composepdfviewer.utils.getFileName
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.File
import java.net.URI

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun copyPdfToInternalStorage() {
        viewModelScope.launch {
            val outputFile = File(context.filesDir, "sample.pdf")
            context.assets.open("sample.pdf").use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }

    fun onPdfSelected(uri: Uri) {
        val fileName = getFileName(uri, context)
        fileName?.let {
            copyPdfToInternalStorage(fileName, uri)
        }
    }

    private fun copyPdfToInternalStorage(fileName: String, uri: Uri) {
        viewModelScope.launch {
            val outputFile = File(context.filesDir, fileName)
            val inputFile = File(URI.create(uri.toString()))
            inputFile.inputStream().use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }
}