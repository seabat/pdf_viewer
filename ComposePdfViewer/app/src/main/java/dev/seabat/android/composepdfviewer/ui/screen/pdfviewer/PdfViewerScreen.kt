package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.UiStateType
import dev.seabat.android.composepdfviewer.ui.component.LoadingComponent

@Composable
fun PdfViewerScreen(
    modifier: Modifier = Modifier,
    viewModel: PdfViewerViewModel = hiltViewModel(),
    pdf: PdfEntity,
) {
    val uiState by viewModel.uiState.collectAsState()
    PdfViewerScreenContent(
        uiState
    )
}

@Composable
fun PdfViewerScreenContent(
    uiState: PdfViewerUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState.state) {
        is UiStateType.Loading -> {
            LoadingComponent()
        }
        is UiStateType.Loaded -> {
        }
        is UiStateType.Error -> {
        }
    }
}