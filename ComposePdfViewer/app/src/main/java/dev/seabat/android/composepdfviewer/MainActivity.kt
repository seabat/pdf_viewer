package dev.seabat.android.composepdfviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerApp
import dev.seabat.android.composepdfviewer.ui.theme.ComposePdfViewerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePdfViewerTheme {
                PdfViewerApp()
            }
        }

        viewModel.copyPdfToInternalStorage()
    }
}

